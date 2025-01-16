package com.biuea.objectpractice.kotlinpractice

import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation

object Stringify {
    /* KClass - 코틀린 클래스
     * 클래스::class
     * 인스턴스::class
     * 코틀린 리플렉션은 코틀린 내부에서 컴파일 과정에 도출되는 결과물
     *
     * KClass.members - 속성, 메소드 일체
     * Collection<KCallable<*>>
     *
     * KCallable - 호출 가능한 객체
     * KProperty - 속성
     * KProperty.name - 속성명
     * KProperty.getter : KCallable.param: Any?
     *
     * KFunction - 함수, 메서드
     * ::함수, 인스턴스::메서드
     */
    private fun StringBuilder.jsonObject(target: Any): String {
        wrap('{', '}') {
            target::class.members.filterIsInstance<KProperty<*>>()
                .joinTo(::comma) {
                    jsonValue(it.findAnnotation<Name>()?.name?: it.name)
                    this.append(':')
                    jsonValue(it.getter.call(target))
                }
        }

        return "$this"
    }

    fun StringBuilder.jsonValue(value: Any?) {
        when(value) {
            null -> this.append("null")
            is String -> jsonString(value)
            is Boolean, is Number -> this.append("$value")
            is List<*> -> jsonList(value)
            else -> jsonObject(value)
        }
    }

    fun stringify(value: Any) = StringBuilder().run {
        jsonValue(value)
        this.toString()
    }

    private fun StringBuilder.jsonString(v: String) = append(""""${v.replace("\"", "\\\"")}"""")

    private fun StringBuilder.jsonList(target: List<*>) {
        wrap('[', ']') {
            target.joinTo(::comma) { jsonValue(it) }
        }
    }
}

fun <T> Iterable<T>.joinTo(sep: () -> Unit, transform: (T) -> Unit) {
    forEachIndexed { count, element ->
        if (count != 0) sep()
        transform(element)
    }
}

fun StringBuilder.comma() {
    append(',')
}

fun StringBuilder.wrap(begin: Char, end: Char, block: () -> Unit) {
    append(begin)
    block()
    append(end)
}

@Target(AnnotationTarget.PROPERTY)
annotation class Ex

@Target(AnnotationTarget.PROPERTY)
annotation class Name(val name: String)