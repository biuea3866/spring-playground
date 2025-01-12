package com.biuea.objectpractice.movie_practice

internal class Reservation private constructor(
    private var _customer: Customer,
    private var _screening: Screening,
    private var _fee: Money,
    private var _audienceCount: Int
) {
    val customer get() = _customer
    val screening get() = _screening
    val fee get() = _fee
    val audienceCount get() = _audienceCount

    // 영화를 예매하기 위해 객체들을 생성한다.
    // 이 객체들은 서로의 메서드를 호출하여 상호작용한다.
    companion object {
        fun create(
            customer: Customer,
            screening: Screening,
            fee: Money,
            audienceCount: Int
        ): Reservation {
            return Reservation(customer, screening, fee, audienceCount)
        }
    }
}