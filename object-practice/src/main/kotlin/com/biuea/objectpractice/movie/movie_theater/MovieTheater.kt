package com.biuea.objectpractice.movie.movie_theater

import com.biuea.objectpractice.movie.reservation.Reservation
import com.biuea.objectpractice.movie.screening.Screening
import com.biuea.objectpractice.movie.screening.Seat
import com.biuea.objectpractice.movie.ticket_holder.TicketHolder
import com.biuea.objectpractice.movie.movie.Movie

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
     * @param ticketHolders 티켓 소지자, 좌석
     */
    fun reserve(
        movie: Movie,
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
            val discountFee = movie.calculate()
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

    private fun checkTicketHolderAndSeatCount(
        ticketHolders: Set<TicketHolder>,
        seats: Set<Seat>
    ) {
        require(ticketHolders.size == seats.size) { "티켓 소지자와 좌석 수가 일치하지 않습니다." }
    }
}