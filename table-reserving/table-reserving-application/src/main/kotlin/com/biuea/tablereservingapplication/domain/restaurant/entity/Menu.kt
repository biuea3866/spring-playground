package com.biuea.tablereservingapplication.domain.restaurant.entity

import com.biuea.tablereservingapplication.core.Id
import com.biuea.tablereservingapplication.domain.restaurant.vo.MenuImage
import java.time.ZonedDateTime

class Menu private constructor(
    private val _id: Id,
    private val _restaurantId: Id,
    private var _price: Int,
    private var _name: String,
    private val _menuImages: MutableList<MenuImage>,
    private val _createdAt: ZonedDateTime,
    private var _updatedAt: ZonedDateTime,
    private var _deletedAt: ZonedDateTime?
) {
    val id get() = this._id
    val restaurantId get() = this._restaurantId
    val price get() = this._price
    val name get() = this._name

    fun updatePrice(price: Int): Menu {
        this._price = price
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    fun deleteMenu(): Menu {
        this._deletedAt = ZonedDateTime.now()

        return this
    }

    fun addImages(images: Set<MenuImage>): Menu {
        this._menuImages.addAll(images)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    fun removeImages(images: Set<MenuImage>): Menu {
        this._menuImages.removeAll(images)
        this._updatedAt = ZonedDateTime.now()

        return this
    }

    companion object {
        fun create(
            restaurantId: Id,
            price: Int,
            name: String,
            menuImages: List<MenuImage>
        ): Menu {
            return Menu(
                _id = Id(0L),
                _restaurantId = restaurantId,
                _price = price,
                _name = name,
                _menuImages = menuImages.toMutableList(),
                _createdAt = ZonedDateTime.now(),
                _updatedAt = ZonedDateTime.now(),
                _deletedAt = null
            )
        }
    }
}