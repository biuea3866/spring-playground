package com.biuea.objectpractice.kotlinpractice

class Calc {
    /**
     * [...]: Character class
     * [^...]: Exception character class
     * \d: Any digit (0-9)
     */
    val trim = """[^.\d-+*/]""".toRegex() // 숫자, 소수점, 음수, 덧셈, 뺄셈, 곱셈, 나눗셈을 제외한 모든 문자를 찾는다.
    // (): capture group은 메모리에 담긴다.
    // (?:): non-capture group은 메모리에 담기지 않는다.
    // (..|..): OR 연산자
    // ?: 0 or 1
    // +: 적어도 1개 이상
    val groupMD = """((?:\+|\+-)?[.\d]+)([*/])(?:\+/\+=)?([.\d]+)""".toRegex()

    // fold: 여러 개의 그룹을 하나로 합치는 함수
    fun foldGroup(v: String): Double = groupMD.findAll(v).fold(0.0) { acc, curr ->
        val (_, left, op, right) = curr.groupValues
        val leftValue = left.replace("+", "").toDouble()
        val rightValue = right.replace("+", "").toDouble()
        val result = when(op) {
            "*" -> leftValue * rightValue
            "/" -> leftValue / rightValue
            else -> throw IllegalArgumentException("Unknown operator: $op")
        }
        acc + result
    }

    fun trim(v: String): String {
        return v.replace(trim, "")
    }

    fun repMtoPM(v: String): String {
        return v.replace("-", "+-")
    }
}