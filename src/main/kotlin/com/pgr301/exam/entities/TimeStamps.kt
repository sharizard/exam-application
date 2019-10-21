package com.pgr301.exam.entities

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.ZonedDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class TimeStamps(
        @CreationTimestamp val createdAt: ZonedDateTime = ZonedDateTime.now(),
        @UpdateTimestamp var updatedAt: ZonedDateTime = ZonedDateTime.now()
)