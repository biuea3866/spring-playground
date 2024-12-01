package com.biuea.tablereservingapplication.domain.restaurant.aggregate

import com.biuea.tablereservingapplication.core.Aggregate
import com.biuea.tablereservingapplication.core.DomainEvent
import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.event.CloseRestaurantEvent
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEvent
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEventPayload
import com.biuea.tablereservingapplication.domain.restaurant.entity.Menu
import com.biuea.tablereservingapplication.domain.restaurant.event.CloseRestaurantEventPayload
import com.biuea.tablereservingapplication.domain.restaurant.vo.RestaurantCertificate
import java.time.ZonedDateTime

/**
 * 식당 어그리거트
 * @param _id 식당 ID
 * @param _name 식당 이름
 * @param _address 식당 주소
 * @param _phoneNumber 식당 전화번호
 * @param _description 식당 설명
 * @param _category 식당 카테고리
 * @param _status 식당 상태
 * @param _ownerId 사장님 ID
 * @param _menus 메뉴 리스트
 * @param _restaurantCertificate 식당 인증서
 * @param _openedAt 오픈 일시
 * @param _closedAt 클로즈 일시
 * @param _createdAt 생성일시
 * @param _updatedAt 수정일시
 * @param _deletedAt 삭제일시
 */
class RestaurantAggregation private constructor(
    private val _id: Id,
    private var _name: String,
    private var _address: String,
    private var _phoneNumber: String,
    private val _description: String,
    private val _category: String,
    private var _status: RestaurantStatus,
    private val _ownerId: Id,
    private var _menus: MutableList<Menu>,
    private var _restaurantCertificate: RestaurantCertificate?,
    private var _openedAt: ZonedDateTime?,
    private var _closedAt: ZonedDateTime?,
    private val _createdAt: ZonedDateTime,
    private var _updatedAt: ZonedDateTime,
    private var _deletedAt: ZonedDateTime?
): Aggregate() {
    val id: Id get() = this._id
    val name: String get() = this._name
    val address: String get() = this._address
    val phoneNumber: String get() = this._phoneNumber
    val description: String get() = this._description
    val category: String get() = this._category
    val status: RestaurantStatus get() = this._status
    val ownerId: Id get() = this._ownerId
    val menus: List<Menu> get() = this._menus
    val openedAt: ZonedDateTime? get() = this._openedAt
    val closedAt: ZonedDateTime? get() = this._closedAt

    /**
     * 식당 오픈
     * @return RestaurantAggregation
     *
     * @throws IllegalArgumentException 식당이 CLOSED 상태가 아닐 때
     * @throws IllegalArgumentException 식당이 이미 오픈된 상태일 때
     *
     * 1. 식당 오픈 관련 상태 체크
     * 2. 식당 오픈 관련 상태 변경
     * 3. 오픈 식당 이벤트 등록
     */
    fun openRestaurant(): RestaurantAggregation {
        require(this._status in RestaurantStatus.availableOpen()) { "식당은 CLOSED 상태일 때만 오픈할 수 있습니다." }
        checkNotNull(this._openedAt == null) { "식당은 이미 오픈된 상태입니다." }

        this._openedAt = ZonedDateTime.now()
        this._status = RestaurantStatus.OPENED

        this.addDomainEvents(
            setOf(
                OpenRestaurantEvent(
                    occurredAt = ZonedDateTime.now(),
                    event = "event.restaurant.opened",
                    payload = OpenRestaurantEventPayload(
                        restaurantId = this._id,
                        ownerId = this._ownerId,
                        openTime = this._openedAt!!
                    )
                )
            )
        )

        return this
    }

    /**
     * 식당 마감
     * @param publish 이벤트 발행 함수
     * @return RestaurantAggregation
     *
     * @throws IllegalArgumentException 식당이 OPEN 상태가 아닐 때
     * @throws IllegalArgumentException 식당이 이미 마감된 상태일 때
     *
     * 1. 식당 마감 관련 상태 체크
     * 2. 식당 마감 관련 상태 변경
     * 3. 마감 식당 이벤트 발행
     */
    fun close(): RestaurantAggregation {
        require(this._status in RestaurantStatus.availableClose()) { "식당은 OPENED 상태일 때만 CLOSED할 수 있습니다." }
        checkNotNull(this._closedAt != null) { "식당은 오픈되지 않은 상태에서 CLOSED할 수 없습니다." }

        this._closedAt = ZonedDateTime.now()
        this._status = RestaurantStatus.CLOSED

        this.addDomainEvents(
            setOf(
                CloseRestaurantEvent(
                    occurredAt = ZonedDateTime.now(),
                    event = "event.restaurant.closed",
                    payload = CloseRestaurantEventPayload(
                        restaurantId = this._id,
                        restaurantName = this._name,
                        closeTime = this._closedAt!!
                    )
                )
            )
        )

        return this
    }

    fun delete(): RestaurantAggregation {
        this._deletedAt = ZonedDateTime.now()

        return this
    }

    /**
     * 식당 인증서 변경
     * @param bucket 인증서 버킷
     * @param key 인증서 키
     *
     * 1. 식당 인증서 변경
     */
    fun changeRestaurantCertificate(
        bucket: String,
        key: String
    ): RestaurantAggregation {
        this._restaurantCertificate = RestaurantCertificate.create(
            bucket = bucket,
            key = key
        )
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    /**
     * 식당 메뉴 추가
     * @param menus 메뉴 리스트
     *
     * 1. 식당 메뉴 추가
     */
    fun addMenus(menus: List<Menu>): RestaurantAggregation {
        this._menus.addAll(menus)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    /**
     * 식당 메뉴 제거
     * @param menus 메뉴 리스트
     *
     * 1. 식당 메뉴 제거
     */
    fun deleteMenus(menus: List<Menu>): RestaurantAggregation {
        this._menus.removeAll(menus)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    /**
     * 식당 메뉴 변경
     * @param menus 메뉴 리스트
     *
     * 1. 식당 메뉴 변경
     */
    fun updateMenus(menus: List<Menu>): RestaurantAggregation {
        this._menus = menus.toMutableList()
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    companion object {
        fun create(
            name: String,
            address: String,
            phoneNumber: String,
            description: String,
            category: String,
            ownerId: Id,
            menus: List<Menu>
        ): RestaurantAggregation {
            return RestaurantAggregation(
                _id = Id(0L),
                _name = name,
                _address = address,
                _phoneNumber = phoneNumber,
                _description = description,
                _category = category,
                _status = RestaurantStatus.PENDING,
                _ownerId = ownerId,
                _menus = menus.toMutableList(),
                _restaurantCertificate = null,
                _openedAt = null,
                _closedAt = null,
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}

enum class RestaurantStatus {
    PENDING,
    OPENED,
    CLOSED
    ;

    companion object {
        fun availableOpen() = setOf(CLOSED)
        fun availableClose() = setOf(OPENED)
    }
}