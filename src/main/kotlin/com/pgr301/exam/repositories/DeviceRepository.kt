package com.pgr301.exam.repositories

import com.pgr301.exam.entities.Device
import com.pgr301.exam.entities.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
interface DeviceRepository : CrudRepository<Device, Long>, DeviceRepositoryCustom

@Transactional
interface DeviceRepositoryCustom {
    fun createDevice(name: String, owner: String) : Long

    fun createMeasurement(deviceId: Long, sievert: String, longitude: Long, latitude: Long, readingTime: LocalDateTime) : Long
}

@Repository
@Transactional
class DeviceRepositoryCustomImpl : DeviceRepositoryCustom {
    @Autowired
    private lateinit var em: EntityManager

    override fun createDevice(name: String, owner: String) : Long {
        val entity = Device(owner = owner, name = name)
        em.persist(entity)
        return entity.id!!
    }

    override fun createMeasurement(deviceId: Long, sievert: String, longitude: Long, latitude: Long, readingTime: LocalDateTime) : Long {
        val entity = Measurement(deviceId = deviceId, sievert = sievert, longitude = longitude, latitude = latitude, readingTime = readingTime)
        em.persist(entity)
        return entity.id!!
    }
}

