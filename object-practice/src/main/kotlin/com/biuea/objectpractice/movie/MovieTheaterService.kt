package com.biuea.objectpractice.movie

import org.springframework.stereotype.Service

@Service
class MovieTheaterService {
    fun reserve(dto: ReserveDTO) {
        dto.movieTheater.reserve(
            movie = dto.movie,
            screening = dto.screening,
            ticketHolders = dto.ticketHolders,
            seats = dto.seats
        )
    }

    data class ReserveDTO(
        val movieTheater: MovieTheater,
        val movie: Movie,
        val screening: Screening,
        val ticketHolders: Set<TicketHolder>,
        val seats: Set<Seat>
    )
}