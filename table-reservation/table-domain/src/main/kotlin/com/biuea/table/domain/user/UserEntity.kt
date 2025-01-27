package com.biuea.table.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserEntity(

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}