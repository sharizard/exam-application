package com.pgr301.exam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import org.springframework.beans.factory.annotation.Autowired



@SpringBootApplication
class ExamApplication {
//    @Autowired
//    private lateinit var meterRegistry: SimpleMeterRegistry
}

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
