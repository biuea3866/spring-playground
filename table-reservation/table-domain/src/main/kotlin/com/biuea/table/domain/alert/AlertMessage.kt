package com.biuea.table.domain.alert

import org.springframework.stereotype.Component

enum class MessageType {
    MAIL,
    KAKAO,
    SMS
}

interface AlertMessage

class MailMessage(
    val subject: String,
    val content: String,
    val to: String,
    val from: String,
    val cc: String? = null,
    val bcc: String? = null
): AlertMessage

class KakaoMessage(
    val subject: String,
    val content: String,
    val to: String,
    val from: String,
): AlertMessage

class SmsMessage(
    val subject: String,
    val content: String,
    val to: String,
    val from: String,
): AlertMessage
