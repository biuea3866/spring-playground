package com.biuea.table.domain.alert

interface AlertMessageBuilder {
    fun build(): AlertMessage
}

class MailMessageBuilder: AlertMessageBuilder {
    private lateinit var subject: String
    private lateinit var content: String
    private lateinit var to: String
    private lateinit var from: String
    private var cc: String? = null
    private var bcc: String? = null

    fun init(
        subject: String,
        content: String,
        to: String,
        from: String,
        cc: String? = null,
        bcc: String? = null
    ): MailMessageBuilder {
        this.subject = subject
        this.content = content
        this.to = to
        this.from = from
        this.cc = cc
        this.bcc = bcc

        return this
    }

    override fun build(): AlertMessage {
        return MailMessage(
            subject = subject,
            content = content,
            to = to,
            from = from,
            cc = cc,
            bcc = bcc
        )
    }
}

class KakaoMessageBuilder: AlertMessageBuilder {
    private lateinit var subject: String
    private lateinit var content: String
    private lateinit var to: String
    private lateinit var from: String

    fun init(
        subject: String,
        content: String,
        to: String,
        from: String
    ): KakaoMessageBuilder {
        this.subject = subject
        this.content = content
        this.to = to
        this.from = from

        return this
    }

    override fun build(): AlertMessage {
        return KakaoMessage(
            subject = subject,
            content = content,
            to = to,
            from = from
        )
    }
}

class SmsMessageBuilder: AlertMessageBuilder {
    private lateinit var subject: String
    private lateinit var content: String
    private lateinit var to: String
    private lateinit var from: String

    fun init(
        subject: String,
        content: String,
        to: String,
        from: String
    ): SmsMessageBuilder {
        this.subject = subject
        this.content = content
        this.to = to
        this.from = from

        return this
    }

    override fun build(): AlertMessage {
        return SmsMessage(
            subject = subject,
            content = content,
            to = to,
            from = from
        )
    }
}