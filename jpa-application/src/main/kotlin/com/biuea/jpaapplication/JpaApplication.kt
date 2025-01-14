package com.biuea.jpaapplication

import jakarta.persistence.Persistence
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaApplication

fun main(args: Array<String>) {
    runApplication<JpaApplication>(*args)
    // jpa-application이란 이름으로 entity manager를 생성
    val entityManagerFactory = Persistence.createEntityManagerFactory("hello")
    val entityManager = entityManagerFactory.createEntityManager()
    val tx = entityManager.transaction
    tx.begin()
    // 비영속
    val member = Member(name = "hello").apply { this.id = 0L }
    // 영속
    entityManager.persist(member)
    tx.commit()
    entityManager.close()
    entityManagerFactory.close()
}