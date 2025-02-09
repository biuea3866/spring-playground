package com.biuea.table.domain.payment

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.ZonedDateTime

@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE point_history SET deleted_at = NOW() WHERE id = ?")
@Table(name = "point_history")
@Entity
class PointHistory(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "point_id",
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    val point: Point,

    @Column(name = "reason")
    val reason: String,

    @Column(name = "amount")
    val amount: Int,

    @Column(name = "issued_at")
    val issuedAt: ZonedDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}