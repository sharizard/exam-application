package com.pgr301.exam.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Device(
        @Id @GeneratedValue val id: Long? = null,
        @NotNull var owner: String? = null,
        @NotBlank var name: String? = null
) : TimeStamps()

