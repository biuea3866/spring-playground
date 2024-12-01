package com.biuea.tablereservingapplication.core

import com.biuea.tablereservingapplication.domain.restaurant.aggregate.RestaurantAggregation
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEvent
import com.biuea.tablereservingapplication.domain.restaurant.event.OpenRestaurantEventPayload
import jakarta.transaction.Transactional
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.ServiceLoader.Provider
import kotlin.reflect.KClass

fun interface Facade: (FacadeDTO) -> Unit

interface FacadeDTO

enum class FacadeType {
    DO_SOMETHING
}

@Component
class FacadeFactory(
    private val facadeList: Map<FacadeType, Facade>
) {
    fun getFacade(type: FacadeType): Facade {
        return this.facadeList[type]?: throw IllegalArgumentException("Facade not found")
    }
}

@Component
class DoSomethingFacade(
    private val restaurantAggregation: RestaurantAggregation,
    private val applicationEventPublisher: ApplicationEventPublisher
): Facade {
    @Transactional
    override fun invoke(dto: FacadeDTO) {
        val restaurant = this.restaurantAggregation.openRestaurant()
        restaurant.pullEvents().forEach { applicationEventPublisher.publishEvent(it) }
    }
}

class DoSomethingDTO(
    val restaurantId: Long,
): FacadeDTO

class Controller(
    private val facadeFactory: FacadeFactory
) {
    fun callOpenFacade() {
        this.facadeFactory.getFacade(FacadeType.DO_SOMETHING)
            .invoke(DoSomethingDTO(0L))
    }
}