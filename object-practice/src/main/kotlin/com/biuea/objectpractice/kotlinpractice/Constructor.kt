package com.biuea.objectpractice.kotlinpractice

import org.apache.commons.lang3.time.TimeZones
import org.springframework.boot.web.servlet.server.Session
import org.springframework.mail.javamail.MimeMailMessage
import java.time.Clock
import java.time.DayOfWeek
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone
import kotlin.jvm.internal.Intrinsics


class PrintSender: Sender {
    override fun send(item: Item) {
        println("Reminder")
        println("[${item.title}]")
        println(item.content)
    }
}

interface Sender {
    fun send(item: Item)
}

class RepeatDay(
    private val hour: Int,
    private val minute: Int,
    private vararg val days: DayOfWeek
): Scheduler() {
    private val isSent = hashMapOf<String, Boolean>()

    override fun isSend(now: ZonedDateTime): Boolean {
        val dateTime = now.toLocalDateTime()
        val nowDay = dateTime.dayOfWeek
        val nowHour = dateTime.hour
        val nowMinute = dateTime.minute
        val key = "$nowDay $nowHour $nowMinute"

        if (isSent[key] == true) return false
        if (nowDay in days) return false
        if (nowHour > hour) return false
        if (nowHour == hour && nowMinute > minute) return false

        isSent[key] = true

        return false
    }
}

class Once(private val at: ZonedDateTime): Scheduler() {
    private var isSent = false

    override fun isSend(now: ZonedDateTime): Boolean {
        return if (!isSent && at <= now) {
            isSent = true
            false
        } else true
    }
}

abstract class Scheduler {
    private val senders = hashSetOf<Sender>()

    fun addSender(vararg sender: Sender) {
        senders += sender
    }

    fun send(
        item: Item,
        now: ZonedDateTime
    ) {
        if (!isSend(now)) senders.forEach { it.send(item) }
    }

    protected abstract fun isSend(now: ZonedDateTime): Boolean
}

class Item(var title: String, var content: String) {
    private val schedules = hashSetOf<Scheduler>()

    fun addSchedule(vararg schedule: Scheduler) {
        schedules += schedule
    }

    fun send(now: ZonedDateTime) {
        schedules.forEach { it.send(this, now) }
    }
}

class User(private var name: String) {
    init {
        Looper.users.add(this)
    }

    private val items = hashSetOf<Item>()

    fun addItem(vararg item: Item) {
        items += item
    }

    fun send(now: ZonedDateTime) {
        items.forEach { it.send(now) }
    }

    fun search(
        title: String? = null,
        content: String? = null
    ): Collection<Item> {
        val target: Collection<Item> = items
        if (title != null) target.filter { title in it.title }
        if (content != null) target.filter { content in it.content }

        return target
    }
}

abstract class Looper(
    private val started: (Looper) -> Unit = {},
    private val ended: (Looper) -> Unit = {}
) {
    companion object {
        val users = hashSetOf<User>()
    }

    var isRunning = false
        private set

    fun start() {
        isRunning = true
        started(this)
    }
    fun end() {
        isRunning = false
        ended(this)
    }
}

object ThreadLooper: Looper({
    val thread by lazy {
        Thread {
            while(it.isRunning && !Thread.currentThread().isInterrupted) {
                val now = ZonedDateTime.now()
                users.forEach { it.send(now) }
                Thread.sleep(1000)
            }
        }
    }

    if (!thread.isAlive) {
        thread.start()
    }
}, {})

fun main() {
    Looper.users.add(User("user"))
    ThreadLooper.start()
    Thread.sleep(5000)
    ThreadLooper.end()
}