package com.pgr301.exam.controllers

import com.google.common.base.Throwables
import com.pgr301.exam.Metrics
import com.pgr301.exam.entities.Measurement
import com.pgr301.exam.repositories.DeviceRepository
import com.pgr301.exam.repositories.MeasurementRepository
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

@RequestMapping(path = ["/devices/{deviceId}/measurements"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
@RestController
class MeasurementController : ActionController() {
    val logger: Logger = LoggerFactory.getLogger(DeviceController::class.java)
    val warnMarker: Marker = MarkerFactory.getMarker("WARN")
    val infoMarker: Marker = MarkerFactory.getMarker("INFO")

    @Autowired
    lateinit var metrics: Metrics

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @GetMapping
    fun index(): ResponseEntity<WrappedResponse<List<Measurement>>> {
        metrics.indexMeasurementCounter().increment()
        return response(status = HttpStatus.OK, data = measurementRepository.findAll().toList())
    }

    @PostMapping(consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun create(@RequestBody measurement: Measurement, @PathVariable("deviceId") deviceId: String): ResponseEntity<WrappedResponse<Measurement>> {
        metrics.createMeasurementCounter().increment()

        if (measurement.id != null || measurement.latitude == null || measurement.longitude == null ||
                measurement.value == null || measurement.unit == null) {
            return response(status = HttpStatus.BAD_REQUEST)
        }

        val id: Long?
        try {
            id = measurementRepository.createMeasurement(
                    value = measurement.value!!,
                    unit = measurement.unit!!,
                    latitude = measurement.latitude!!,
                    longitude = measurement.longitude!!,
                    readingTime = measurement.readingTime!!,
                    device = deviceRepository.findById(deviceId.toLong()).get())
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return response(status = HttpStatus.BAD_REQUEST, message = e.message)
            }
            throw e
        }

        return response(status = HttpStatus.CREATED, data = measurementRepository.findById(id).get())
    }

    @GetMapping(path = ["/{id}"] ,consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun show(@PathVariable("deviceId") deviceId: String, @PathVariable("id") id: String): ResponseEntity<WrappedResponse<Measurement>> {
        metrics.showMeasurementCounter().increment()

        return try {
            val device = deviceRepository.findById(deviceId.toLong())
            if (device.isPresent) {
                logger.info(infoMarker, "Fetched device with id: $id")
                response(status = HttpStatus.OK, data = measurementRepository.findById(id.toLong()).get())
            } else {
                logger.error(warnMarker, "Tried to access nonexistent device $id")
                response(status = HttpStatus.NOT_FOUND, message = "Couldn't find Device with id: $id")
            }
        } catch (e: Exception) {
            logger.error(warnMarker, "Tried to access nonexistent device")
            response(message = e.message, status = HttpStatus.NOT_FOUND)
        }
    }
}