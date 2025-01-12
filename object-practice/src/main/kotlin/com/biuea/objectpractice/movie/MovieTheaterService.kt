package com.biuea.objectpractice.movie

import org.springframework.stereotype.Service

@Service
class MovieTheaterService(
    private val movieRepository: MovieRepository
) {
    fun reserve(dto: ReserveDTO) {
        dto.movieTheater.reserve(
            movie = dto.movie,
            screening = dto.screening,
            ticketHolders = dto.ticketHolders,
            seats = dto.seats
        )
    }

    fun decideMovieDiscountPolicy() {

    }

    fun getMovies(): List<MovieDiscountPolicy> {
        return movieRepository.getMovies()
    }

    data class ReserveDTO(
        val movieTheater: MovieTheater,
        val movie: MovieDiscountPolicy,
        val screening: Screening,
        val ticketHolders: Set<TicketHolder>,
        val seats: Set<Seat>
    )
}