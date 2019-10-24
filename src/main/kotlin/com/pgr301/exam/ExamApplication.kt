package com.pgr301.exam

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.beans.factory.annotation.Autowired



@SpringBootApplication
class ExamApplication {
    @Autowired
    private lateinit var meterRegistry: MeterRegistry
}

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
