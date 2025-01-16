package com.biuea.objectpractice.salary

class SalarySystem {
    val employeeTable: HashMap<Long, Employee> = hashMapOf(
        1L to Employee("김인혁", 1000),
        2L to Employee("이재문", 1500),
        3L to Employee("양재욱", 2000)
    )
    val taxRateTable: HashMap<Int, Double> = hashMapOf(
        2024 to 0.1
    )

    // 직원의 급여를 계산한다.
    fun main(type: String) {
        printRequestTaxRate(0.1)

        // 전체 직원 급여 계산이 추가됨에 따라
        // 최상위 클라이언트에서 각 직원의 급여를 계산하는 방법, 전체 직원 급여를 계산하는 방법을 정의한다.
        when (type) {
            "all" -> calculateAllSalary()
            "each" -> calculateEachSalary()
        }
    }

    fun calculateAllSalary() {
        var totalSalary = 0
        this.employeeTable.forEach { employeeId, Employee ->
            totalSalary += calculateSalary(
                getSalary(employeeId).salary,
                taxRateTable[2025]?: throw IllegalArgumentException("세율이 입력되지 않았습니다.")
            )
        }
    }

    fun calculateEachSalary() {
        this.employeeTable.forEach { (employeeId, Employee) ->
            calculateSalary(
                getSalary(employeeId).salary,
                taxRateTable[2025]?: throw IllegalArgumentException("세율이 입력되지 않았습니다.")
            )
        }
    }

    // 사용자로부터 소득 세율을 입력받는다.
    fun enterTaxRate(
        year: Int = 2025,
        rate: Double
    ) {
        this.taxRateTable[year] = rate
    }

    // 세율을 입력하세요 라는 문장을 화면에 출력한다.
    fun printRequestTaxRate(rate: Double) {
        print("세율을 입력하세요: ")
        enterTaxRate(rate = rate)
    }

    // 직원의 급여를 계산한다.
    fun calculateSalary(
        salary: Int,
        taxRate: Double
    ): Int {
        return calculateSalaryBy(salary, taxRate).also {
            printResult(1L, it)
        }
    }

    // 전역 변수에 저장된 직원의 기본급 정보를 얻는다.
    fun getSalary(employeeId: Long): Employee {
        return this.employeeTable[employeeId]?: throw IllegalArgumentException("해당 직원이 없습니다.")
    }

    // 급여를 계산한다.
    fun calculateSalaryBy(
        salary: Int,
        taxRate: Double
    ): Int {
        return (salary * (1 - taxRate)).toInt()
    }

    fun calculateSalaryBy(): Int {

    }

    // 양식에 맞게 결과를 출력한다.
    fun printResult(
        employeeId: Long,
        salary: Int
    ) {
        println("직원 아이디: $employeeId, 급여: $salary")
    }

    class Employee(
        val name: String,
        val salary: Int
    )
}