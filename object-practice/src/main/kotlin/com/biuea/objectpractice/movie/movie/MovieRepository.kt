package com.biuea.objectpractice.movie.movie

interface MovieRepository {
    fun getMovies(): List<Movie>
}