package com.biuea.objectpractice.movie.screening

import com.biuea.objectpractice.movie.movie.Movie
import java.time.ZonedDateTime

/**
 * 상영관
 * @property _movie 영화
 * @property _screeningDates 상영일
 */
class Screening private constructor(
    private var _movie: Movie?,
    private var _screeningDates: Set<ScreeningDate>,
    private var _seats: Set<Seat>
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

    fun reserveSeat(seat: Seat) {
        this._seats.find { it.seatNumber == seat.seatNumber }?.complete()
            ?: throw IllegalArgumentException("해당 좌석이 존재하지 않습니다.")
    }

    fun checkAvailableScreening() {
        require(_movie != null) { "영화가 등록되지 않았습니다." }
        require(_screeningDates.isNotEmpty()) { "상영일이 등록되지 않았습니다." }
    }

    fun checkAvailableReserve() {
        require(_seats.isNotEmpty()) { "좌석이 등록되지 않았습니다." }
        require(_seats.none { it.isOccupied }) { "모든 좌석이 예약되었습니다." }
    }
}

// 데이터베이스의 쿼리로 처리해야한다.
fun Set<Screening>.pick10ThScreeningOfToday(): Screening? {
    return runCatching { this.filter { it.screeningDates.any { screeningDate -> screeningDate.date.dayOfYear == ZonedDateTime.now().dayOfYear } }[9] }
        .getOrNull()
}

class Seat private constructor(
    private var _seatNumber: String,
    private var _isOccupied: Boolean,
    private var _seatType: SeatType,
) {
    val seatNumber get() = _seatNumber
    val isOccupied get() = _isOccupied
    val seatType get() = _seatType

    fun complete() {
        _isOccupied = true
    }

    enum class SeatType {
        VIP,
        STANDARD
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

