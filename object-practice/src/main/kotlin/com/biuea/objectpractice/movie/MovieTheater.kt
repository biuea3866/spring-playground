package com.biuea.objectpractice.movie

/**
 * 영화관
 */
class MovieTheater private constructor(
    private var _movies: Set<Movie>,
    private var _screenings: Set<Screening>
) {
    val movies get() = _movies
    val screenings get() = _screenings

    /**
     * 영화 예매
     * @param movie 영화
     * @param screening 상영
     * @param ticketHolder 티켓 소지자
     */
    fun reserve(
        movie: Movie,
        screening: Screening,
        ticketHolder: TicketHolder
    ): Reservation {
        val fee = movie.fee
        val discountFee = movie.calculate()
        val title = movie.title

        return Reservation.create(
            screening = screening,
            ticketHolder = ticketHolder,
            originalFee = fee,
            fee = discountFee,
            title = title
        )
    }
}