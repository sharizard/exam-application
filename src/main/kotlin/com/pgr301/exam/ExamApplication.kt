package com.pgr301.exam

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.net.URI


@SpringBootApplication
@EnableSwagger2
class ExamApplication {
    @Autowired
    private lateinit var meterRegistry: MeterRegistry

    @RequestMapping(path = ["/"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
    @RestController
    class HomeController {
        @GetMapping
        fun landing() : ResponseEntity<String>{
            // We just return the api docs as when going to root.
            return ResponseEntity.status(301).location(URI.create("/v2/api-docs")).build()
        }
    }

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("API for REST GeigerDevices")
                .description("Register a Geiger device and measurements on a device")
                .version("1.0")
                .build()
    }
}

fun main(args: Array<String>) {
    runApplication<ExamApplication>(*args)
}
