package com.biuea.objectpractice.movie

import java.time.ZonedDateTime

/**
 * 상영관
 * @property _movie 영화
 * @property _screeningDates 상영일
 */
class Screening private constructor(
    private var _movie: Movie?,
    private var _screeningDates: Set<ScreeningDate>,
) {
    val movie get() = _movie
    val screeningDates get() = _screeningDates

    fun registerMovie(movie: Movie) {
        _movie = movie
    }

    fun addScreeningDate(screeningDate: ScreeningDate) {
        _screeningDates.plus(screeningDate)
    }

    fun removeScreeningDate(screeningDate: ScreeningDate) {
        _screeningDates.minus(screeningDate)
    }
}

class ScreeningDate private constructor(
    private var _date: ZonedDateTime,
    private var _timeSlots: Set<TimeSlot>
) {
    val date get() = _date
    val timeSlots get() = _timeSlots
}

class TimeSlot private constructor(
    private var _sequence: Int,
    private var _startTime: ZonedDateTime
) {
    val sequence get() = _sequence
    val startTime get() = _startTime
}