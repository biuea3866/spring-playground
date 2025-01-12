package com.biuea.objectpractice.movie

interface MovieRepository {
    fun getMovies(): List<Movie<MovieDiscountPolicy>>
}