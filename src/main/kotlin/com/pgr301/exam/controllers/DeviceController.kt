package com.pgr301.exam.controllers

import com.google.common.base.Throwables
import com.pgr301.exam.*
import com.pgr301.exam.entities.Device
import com.pgr301.exam.repositories.DeviceRepository
import io.micrometer.core.annotation.Timed
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException

@RequestMapping(path = ["/devices"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
@RestController
class DeviceController : ActionController() {
    val logger: Logger = LoggerFactory.getLogger(DeviceController::class.java)
    val warnMarker: Marker = MarkerFactory.getMarker("WARN")
    val infoMarker: Marker = MarkerFactory.getMarker("INFO")

    @Autowired
    lateinit var metrics: Metrics

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @Timed(value = "get_devices", percentiles = [0.5, 0.9, 0.99], histogram = true, longTask = true)
    @GetMapping
    fun index(): ResponseEntity<WrappedResponse<List<Device>>> {
        metrics.indexDeviceCounter().increment()
        return response(status= HttpStatus.OK, data = deviceRepository.findAll().toList())
    }

    @Timed(value = "get_device", percentiles = [0.5, 0.9, 0.99], histogram = true)
    @GetMapping(path = ["/{id}"])
    fun show(@PathVariable("id") pathId: String): ResponseEntity<WrappedResponse<Device>> {
        metrics.showDeviceCounter().increment()
        val id: Long
        try {
            id = pathId.toLong()
        } catch (e: Exception) {
            logger.error(warnMarker, "Tried to access nonexistent device")
            return ResponseEntity.status(404).build()
        }

        val device = deviceRepository.findById(id)
        return if (device.isPresent) {
            logger.info(infoMarker, "Fetched device with id: $id")
            response(status = HttpStatus.OK, data = deviceRepository.findById(id).get())
        } else {
            logger.error(warnMarker, "Tried to access nonexistent device")
            response(status = HttpStatus.NOT_FOUND, message = "Couldn't find Device with id: $id")
        }
    }
    @Timed(value = "create_device", percentiles = [0.5, 0.9, 0.99], histogram = true)
    @PostMapping(consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun create(@RequestBody device: Device): ResponseEntity<WrappedResponse<Device>> {
        metrics.createDeviceCounter().increment()

        if (device.id != null || device.name.isNullOrBlank() || device.owner.isNullOrBlank()) {
            return response(status = HttpStatus.BAD_REQUEST)
        }

        val id: Long?
        try {
            id = deviceRepository.createDevice(name = device.name!!, owner = device.owner!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return response(status = HttpStatus.BAD_REQUEST)
            }
            throw e
        }

        return response(status = HttpStatus.CREATED, data = deviceRepository.findById(id).get())
    }
}