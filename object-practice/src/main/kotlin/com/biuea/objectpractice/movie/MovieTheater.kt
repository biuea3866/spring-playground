package com.biuea.objectpractice.movie

/**
 * 영화관
 */
class MovieTheater private constructor(
    private var _movies: MutableSet<Movie<MovieDiscountPolicy>>,
    private var _screenings: MutableSet<Screening>
) {
    val movies get() = _movies
    val screenings get() = _screenings

    /**
     * 영화 예매
     * @param movie 영화
     * @param screening 상영
     * @param ticketHolders 티켓 소지자, 좌석
     */
    fun reserve(
        movie: Movie<MovieDiscountPolicy>,
        screening: Screening,
        ticketHolders: Set<TicketHolder>,
        seats: Set<Seat>
    ): Set<Reservation> {
        checkTicketHolderAndSeatCount(ticketHolders, seats)
        screening.checkAvailableScreening()
        screening.checkAvailableReserve()

        return ticketHolders.zip(seats).map { (ticketHolder, seat) ->
            screening.reserveSeat(seat)
            val fee = movie.fee
            val discountFee = movie.calculate(screening, ticketHolders.count())
            val title = movie.title

            Reservation.create(
                screening = screening,
                ticketHolder = ticketHolder,
                amount = fee,
                discountFee = discountFee,
                title = title
            )
        }.toSet()
    }

    /**
     * 영화관 오픈
     * 날마다 오픈이 되며 영화관, 상영관의 객체가 변경된다.
     */
    fun open(
        movies: Set<Movie<MovieDiscountPolicy>>,
        screenings: Set<Screening>
    ) {
        _screenings.addAll(screenings)
        _movies.addAll(movies)
    }

    fun close() {
        this._screenings.clear()
        this._movies.clear()
    }

    private fun checkTicketHolderAndSeatCount(
        ticketHolders: Set<TicketHolder>,
        seats: Set<Seat>
    ) {
        require(ticketHolders.size == seats.size) { "티켓 소지자와 좌석 수가 일치하지 않습니다." }
    }
}