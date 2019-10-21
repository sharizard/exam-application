package com.pgr301.exam.entities

import com.sun.istack.NotNull
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Measurement(
        @Id @GeneratedValue val id: Long? = null,
        @NotNull var deviceId: Long? = null,
        @NotBlank var sievert: String? = null,
        @NotNull var readingTime: LocalDateTime? = null,
        @NotNull var longitude: Long? = null,
        @NotNull var latitude: Long? = null
) : TimeStamps()

