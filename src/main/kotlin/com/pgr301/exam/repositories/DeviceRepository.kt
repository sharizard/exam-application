package com.pgr301.exam.repositories

import com.pgr301.exam.entities.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
interface DeviceRepository : CrudRepository<Device, Long>, DeviceRepositoryCustom

@Transactional
interface DeviceRepositoryCustom {
    fun createDevice(name: String, owner: String) : Long
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
}

