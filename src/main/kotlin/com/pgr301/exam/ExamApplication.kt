package com.pgr301.exam

import com.google.gson.Gson
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class ExamApplication {
    @Autowired
    private lateinit var meterRegistry: MeterRegistry

    @RequestMapping(path = ["/"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
    @RestController
    class HomeController {
        @GetMapping
        fun index(): ResponseEntity<String> {
            return ResponseEntity.ok("Use the endpoints at '/devices' to create/find device(s) or /device/{id}/measurement to create measurement on a device")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
