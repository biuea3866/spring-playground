package com.biuea.jpaapplication

import jakarta.persistence.*

@Entity
class Member(
    @Column(name = "name")
    val name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
}