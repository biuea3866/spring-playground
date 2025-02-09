package com.biuea.table.domain.payment

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE point SET deleted_at = NOW() WHERE id = ?")
@Table(name = "point")
@Entity
class Point(
    @Column(name = "amount")
    val amount: Int,
    @Column(name = "user_id")
    val userId: Long,
    @OneToMany(
        mappedBy = "point",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val pointHistories: MutableList<PointHistory> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}