package com.pgr301.exam

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExamApplication

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
