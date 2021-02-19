package com.ag.simcapi3.controller

import com.ag.simcapi3.service.QueueService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable

@Controller
class RSocketController (val queueService: QueueService) {

    @MessageMapping("queueposition")
    fun sim(@PathVariable symbol: String) = queueService.queuePositionStream(symbol)
}
