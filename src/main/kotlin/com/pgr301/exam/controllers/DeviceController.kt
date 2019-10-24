package com.pgr301.exam.controllers

import com.google.common.base.Throwables
import com.google.gson.Gson
import com.pgr301.exam.entities.Device
import com.pgr301.exam.entities.Measurement
import com.pgr301.exam.repositories.DeviceRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException


@RequestMapping(path = ["/devices"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
@RestController
class DeviceController {
    val logger: Logger = LoggerFactory.getLogger(DeviceController::class.java)
    val warnMarker: Marker = MarkerFactory.getMarker("WARN")
    val infoMarker: Marker = MarkerFactory.getMarker("INFO")

    @Autowired
    private lateinit var crud: DeviceRepository

    @GetMapping
    fun getAll(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(crud.findAll().map { Gson().toJson(it) })
    }

    @GetMapping(path = ["/{id}"])
    fun getOne(@PathVariable("id") pathId: String?): ResponseEntity<String> {
        val id: Long
        try {
            id = pathId!!.toLong()
        } catch (e: Exception) {
            logger.error(warnMarker, "Tried to access nonexistent device")
            return ResponseEntity.status(404).build()
        }

        val device = crud.findById(id)
        return if (device.isPresent) {
            logger.info(infoMarker, "Fetched device with id: $id")
            ResponseEntity.ok(Gson().toJson(crud.findById(id)))
        } else {
            logger.error(warnMarker, "Tried to access nonexistent device")
            ResponseEntity.status(404).body(Gson().toJson("{\"error\": \"Couldn't find Device with id: $id\"}"))
        }

    }

    @PostMapping(consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun createDevice(@RequestBody device: Device): ResponseEntity<String> {
        if (device.id != null || device.name.isNullOrBlank() || device.owner.isNullOrBlank()) {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try {
            id = crud.createDevice(name = device.name!!, owner = device.owner!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(Gson().toJson(crud.findById(id)))
    }

    @PostMapping(path = ["{deviceId}/measurements"] ,consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun createMeasurement(@RequestBody measurement: Measurement, @PathVariable deviceId: String): ResponseEntity<Long> {
        if (measurement.id != null || measurement.latitude == null ||
                measurement.longitude == null || measurement.sievert == null) {
            return ResponseEntity.status(400).build()
        }

        val id: Long?
        try {
            id = crud.createMeasurement(
                    deviceId = deviceId.toLong(),
                    sievert = measurement.sievert!!,
                    latitude = measurement.latitude!!,
                    longitude = measurement.longitude!!,
                    readingTime = measurement.readingTime!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(id)
    }
}