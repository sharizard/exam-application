package com.pgr301.exam.repositories

import com.pgr301.exam.SievertUnit
import com.pgr301.exam.entities.Device
import com.pgr301.exam.entities.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.transaction.Transactional


@Repository
interface MeasurementRepository : CrudRepository<Measurement, Long>, MeasurementRepositoryCustom

@Transactional
interface MeasurementRepositoryCustom {
    fun createMeasurement(value: BigDecimal, unit: SievertUnit, longitude: BigDecimal, latitude: BigDecimal, readingTime: LocalDateTime, device: Device): Long
}

@Repository
@Transactional
class MeasurementRepositoryCustomImpl : MeasurementRepositoryCustom {
    @Autowired
    private lateinit var em: EntityManager

    override fun createMeasurement(value: BigDecimal, unit: SievertUnit, longitude: BigDecimal, latitude: BigDecimal, readingTime: LocalDateTime, device: Device): Long {
        val entity = Measurement(value = value, unit = unit, longitude = longitude, latitude = latitude, readingTime = readingTime, device = device)
        em.persist(entity)
        return entity.id!!
    }
}