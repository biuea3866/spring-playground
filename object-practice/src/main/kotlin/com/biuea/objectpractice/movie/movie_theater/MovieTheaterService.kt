package com.biuea.objectpractice.movie.movie_theater

import com.biuea.objectpractice.movie.screening.Screening
import com.biuea.objectpractice.movie.screening.Seat
import com.biuea.objectpractice.movie.ticket_holder.TicketHolder
import com.biuea.objectpractice.movie.movie.Movie
import com.biuea.objectpractice.movie.movie.MovieRepository
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

    fun getMovies(): List<Movie> {
        return movieRepository.getMovies()
    }

    data class ReserveDTO(
        val movieTheater: MovieTheater,
        val movie: Movie,
        val screening: Screening,
        val ticketHolders: Set<TicketHolder>,
        val seats: Set<Seat>
    )
}