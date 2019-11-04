package com.pgr301.exam.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import com.pgr301.exam.SievertUnit
import com.sun.istack.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Measurement(
        @NotBlank var value: Double? = null,
        @NotNull var unit: SievertUnit? = null,
        @NotNull var readingTime: LocalDateTime? = null,
        @NotNull var longitude: BigDecimal? = null,
        @NotNull var latitude: BigDecimal? = null,
        @JsonBackReference @NotNull @ManyToOne var device: Device? = null
) : TimeStamps()

