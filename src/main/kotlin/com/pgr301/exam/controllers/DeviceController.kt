package com.pgr301.exam.controllers

import com.fasterxml.jackson.databind.util.JSONPObject
import com.google.common.base.Throwables
import com.google.gson.Gson
import com.pgr301.exam.entities.Device
import com.pgr301.exam.entities.Measurement
import com.pgr301.exam.repositories.DeviceRepository
import jdk.nashorn.internal.runtime.JSONFunctions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException


@RequestMapping(path = ["/devices"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
@RestController
class DeviceController {

    @Autowired
    private lateinit var crud: DeviceRepository

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