package com.ag.simcapi3.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "results")
data class SimResult(
        @Id
        val uuid: String,
        @Column(length = 25000)
        val result: String,
        @Column
        val dps: String,
        @Column
        val time: LocalDateTime)