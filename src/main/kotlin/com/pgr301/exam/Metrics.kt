package com.pgr301.exam

import com.google.common.util.concurrent.AtomicDouble
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class Metrics(val registry: MeterRegistry) {

    var svAtomicDouble = AtomicDouble(0.0)
    var nsvAtomicDouble = AtomicDouble(0.0)
    var msvAtomicDouble = AtomicDouble(0.0)
    var usvAtomicDouble = AtomicDouble(0.0)


    fun indexDeviceCounter(): Counter {
        return registry.counter("index-device-counter")
    }

    fun createDeviceCounter(): Counter {
        return registry.counter("create-device-counter")
    }

    fun createMeasurementCounter(): Counter {
        return registry.counter("create-measurement-counter")
    }

    fun showDeviceCounter(): Counter {
        return registry.counter("show-devices-counter")
    }

    fun showMeasurementCounter(): Counter {
        return registry.counter("show-measurement-counter")
    }

    fun indexMeasurementCounter(): Counter {
        return registry.counter("index-measurements-counter")
    }

    @Bean
    fun sievertSvGauge() {
        registry.gauge("gauge.sievert.sv", svAtomicDouble)
    }

    @Bean
    fun sievertNsvGauge() {
        registry.gauge("gauge.sievert.nsv", nsvAtomicDouble)
    }

    @Bean
    fun sievertMsvGauge() {
        registry.gauge("gauge.sievert.msv", msvAtomicDouble)
    }

    @Bean
    fun sievertUsvGauge() {
        registry.gauge("gauge.sievert.usv", usvAtomicDouble)
    }
}