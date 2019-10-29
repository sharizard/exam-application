package com.pgr301.exam

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class Metrics {

    @Autowired
    private lateinit var meterRegistry: MeterRegistry

    fun indexDeviceCounter(): Counter {
        return meterRegistry.counter("index-device-counter")
    }

    fun createDeviceCounter(): Counter {
        return meterRegistry.counter("create-device-counter")
    }

    fun createMeasurementCounter(): Counter {
        return meterRegistry.counter("create-measurement-counter")
    }

    fun showDeviceCounter(): Counter {
        return meterRegistry.counter("show-devices-counter")
    }

    fun showMeasurementCounter(): Counter {
        return meterRegistry.counter("show-measurement-counter")
    }

    fun indexMeasurementCounter(): Counter {
        return meterRegistry.counter("index-measurements-counter")
    }
}