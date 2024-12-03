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

fun interface Facade<in T> {
    fun execute(dto: T)
}

class FacadeFactory<T>(
    private val instances: Map<KClass<T>, Facade<T>>
) {
    inline fun<reified T> facadeFactory(dto: T): Facade<T> = this.instances[dto::class]
        ?: throw IllegalArgumentException("Facade not found")
}

interface FacadeDTO

@Component
class DoSomethingFacade(
    private val restaurantAggregation: RestaurantAggregation,
    private val applicationEventPublisher: ApplicationEventPublisher
): Facade {
    @Transactional
    override fun execute(dto: FacadeDTO) {
        val restaurant = this.restaurantAggregation.openRestaurant()
        restaurant.pullEvents().forEach { applicationEventPublisher.publishEvent(it) }
    }
}

class Controller(
    private val facadeFactory: FacadeFactory
) {
    fun callOpenFacade() {
        this.facadeFactory.getFacade(FacadeType.DO_SOMETHING)
            .execute(DoSomethingDTO(0L))
    }
}