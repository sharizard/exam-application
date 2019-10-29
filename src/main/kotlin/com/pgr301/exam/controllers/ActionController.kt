package com.pgr301.exam.controllers

import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

open class ActionController {
    fun <T> response(message: String? = null, status: HttpStatus, data: T? = null, uri: URI? = null): ResponseEntity<WrappedResponse<T>> {
        val response = WrappedResponse(
                code = status.value(),
                data = data,
                status = status.name,
                message = message
        )

        return if(uri != null) {
            ResponseEntity.created(uri).body(response)
        } else {
            ResponseEntity.status(status.value()).body(response)
        }
    }
}

class WrappedResponse<T>(
        @ApiModelProperty("The HTTP status code of the response")
        var code: Int,

        @ApiModelProperty("The HTTP status name of the response")
        var status: String,

        @ApiModelProperty("The wrapped payload")
        var data: T? = null,

        @ApiModelProperty("Error message in case where was an error")
        var message: String? = null
)