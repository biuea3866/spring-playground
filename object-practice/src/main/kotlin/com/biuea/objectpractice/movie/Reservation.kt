package com.biuea.objectpractice.movie

import java.time.ZonedDateTime

class Reservation private constructor(
    val id: Long,
    private var _screening: Screening,
    private var _ticketHolder: TicketHolder,
    private var _amount: Money,
    private var _discountFee: Money,
    private var _title: String,
    val reservedAt: ZonedDateTime,
) {
    val screening get() = _screening
    val ticketHolder get() = _ticketHolder
    val discountFee get() = _discountFee
    val amount get() = _amount
    val title get() = _title

    companion object {
        fun create(
            screening: Screening,
            ticketHolder: TicketHolder,
            amount: Money,
            discountFee: Money,
            title: String,
        ): Reservation {
            return Reservation(
                id = 0L,
                _screening = screening,
                _ticketHolder = ticketHolder,
                _amount = amount,
                _discountFee = discountFee,
                _title = title,
                reservedAt = ZonedDateTime.now()
            )
        }
    }
}

/**
 * 로직상으로는 전체 중 오늘 시간을 필터링하나 실제로는 데이터베이스의 쿼리로 처리해야한다.
 */
fun Set<Reservation>.pick10ThReservationOfToday(): Reservation? {
    return runCatching { this.filter { it.reservedAt.dayOfYear == ZonedDateTime.now().dayOfYear }[9] }
        .getOrNull()
}