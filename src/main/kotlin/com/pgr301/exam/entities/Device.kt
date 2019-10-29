package com.pgr301.exam.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Device(
        @NotNull var owner: String? = null,
        @NotBlank var name: String? = null,
        @JsonManagedReference
        @OneToMany(mappedBy = "device", cascade = [CascadeType.ALL])
        var measurements: List<Measurement> = emptyList()
) : TimeStamps()

