package com.biuea.table.application.reservation

import com.biuea.table.application.support.ReservationCoordinator
import com.biuea.table.domain.payment.CouponPaymentPolicy
import com.biuea.table.domain.payment.CouponRepository
import com.biuea.table.domain.payment.PaymentDiscount
import com.biuea.table.domain.payment.PaymentGateway
import com.biuea.table.domain.payment.PaymentHistory
import com.biuea.table.domain.payment.PaymentRepository
import com.biuea.table.domain.payment.PointPaymentPolicy
import com.biuea.table.domain.payment.PointRepository
import com.biuea.table.domain.payment.SelfDiscountPolicy
import com.biuea.table.domain.reservation.ReservationEntity
import com.biuea.table.domain.reservation.ReservationRepository
import com.biuea.table.domain.restaurant.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ReservationRestaurantApplication(
    private val reservationRepository: ReservationRepository,
    private val restaurantRepository: RestaurantRepository,
    private val paymentRepository: PaymentRepository,
    private val paymentGateway: PaymentGateway,
    private val couponRepository: CouponRepository,
    private val pointRepository: PointRepository
) {
    @Transactional
    fun reserveRestaurant(command: ReserveRestaurantCommand) {
        val restaurant = restaurantRepository.findBy(command.restaurantId)
            ?: throw IllegalArgumentException("Restaurant not found")
        restaurant.issueWaitingNumber()
        val reservation = ReservationEntity.create(
            customerId = command.userId,
            restaurantId = restaurant.id,
            aPartyOf = command.aPartyOf,
            startAt = command.startAt,
            endAt = command.endAt,
        )
        ReservationCoordinator().issueWaitingNumber(reservation, restaurant)
        val payment = paymentRepository.findBy(command.userId)
            ?: throw IllegalArgumentException("Payment not found")
        val paymentDiscount = applyPaymentDiscount(
            command.paymentAmount,
            command.point,
            command.couponId,
            command.selfDiscount,
            command.userId
        )
        paymentGateway.pay(paymentDiscount.calculate())
        paymentRepository.update(payment, "${restaurant.name} 에서 ${paymentDiscount.calculate()}를 결제했습니다.", paymentDiscount.calculate(), PaymentHistory.Type.PAYMENT)
    }

    private fun applyPaymentDiscount(
        paymentAmount: Int,
        point: Int?,
        couponId: Long?,
        selfDiscount: Int?,
        userId: Long
    ): PaymentDiscount {
        val paymentDiscount = PaymentDiscount(paymentAmount)
        val coupon = couponId?.let { couponRepository.findBy(it) }
        coupon?.use()
        val point = point?.let { pointRepository.findBy(userId) }
        point?.let { pointRepository.update(point) }

        coupon?.let { paymentDiscount.addDiscountPolicy(CouponPaymentPolicy(it)) }
        point?.let { paymentDiscount.addDiscountPolicy(PointPaymentPolicy(it)) }
        selfDiscount?.let { paymentDiscount.addDiscountPolicy(SelfDiscountPolicy(it)) }

        return paymentDiscount
    }
}

data class ReserveRestaurantCommand(
    val userId: Long,
    val restaurantId: Long,
    val aPartyOf: Int,
    val startAt: ZonedDateTime,
    val endAt: ZonedDateTime,
    val paymentAmount: Int,
    val point: Int?,
    val couponId: Long?,
    val selfDiscount: Int?
)