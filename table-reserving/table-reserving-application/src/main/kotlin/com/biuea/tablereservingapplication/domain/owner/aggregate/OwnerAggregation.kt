package com.biuea.tablereservingapplication.domain.owner.aggregate

import com.biuea.tablereservingapplication.core.Id
import java.time.ZonedDateTime

/**
 * OwnerAggregation
 * @param _id 사장님 ID
 * @param _phoneNumber 사장님 전화번호
 * @param _activeStatus 사장님 활성화 상태
 * @param _gradeStatus 사장님 등급 상태
 * @param _createdAt 생성일시
 * @param _updatedAt 수정일시
 * @param _deletedAt 삭제일시
 * @method isRegisterAvailableRestaurant 식당 등록 가능 여부 확인
 */
class OwnerAggregation private constructor(
    private val _id: Id,
    private val _phoneNumber: String,
    private val _activeStatus: OwnerActiveStatus,
    private val _gradeStatus: OwnerGradeStatus,
    private val _createdAt: ZonedDateTime,
    private val _updatedAt: ZonedDateTime,
    private val _deletedAt: ZonedDateTime?
) {
    val id get() = this._id
    val phoneNumber get() = this._phoneNumber
    val activeStatus get() = this._activeStatus
    val gradeStatus get() = this._gradeStatus

    fun isRegisterAvailableRestaurant() {
        require(this._activeStatus.isActive()) { "사장님의 가입 상태가 ACTIVE 상태가 아닙니다." }
        require(this._gradeStatus in OwnerGradeStatus.availableRegisterRestaurantGrade()) { "사장님의 등급이 NORMAL 혹은 PREMIUM 상태가 아닙니다." }
    }
}

enum class OwnerActiveStatus {
    ACTIVE,
    INACTIVE
    ;

    fun isActive(): Boolean {
        return this == ACTIVE
    }
}

enum class OwnerGradeStatus {
    NORMAL,
    PREMIUM
    ;

    companion object {
        fun availableRegisterRestaurantGrade(): List<OwnerGradeStatus> {
            return listOf(NORMAL, PREMIUM)
        }
    }
}