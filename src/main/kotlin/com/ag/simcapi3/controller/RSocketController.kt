package com.ag.simcapi3.controller

import com.ag.simcapi3.service.SimWorker
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@Controller
class RSocketController (val generate: Generate, val simWorker: SimWorker) {

    @MessageMapping("queueposition")
    fun sim(@PathVariable symbol: String) = generate.generateRandom(symbol)
}

//@RestController
//class Rest(val generate: Generate){
//    @GetMapping(value = ["/queueposition/{symbol}"],produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
//    fun  rnd(@PathVariable symbol: String) = generate.generateRandom(symbol)
//
//    @GetMapping(value = ["/queueposition/{symbol}"],produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
//    fun  queuePositionStream(@PathVariable symbol: String) = generate.generateRandom(symbol)
//}

@Service
class Generate {
    fun generateRandom(symbol: String): Flux<String> {
        val r = Random();

        return Flux
                .interval(Duration.ofSeconds(1))
                .map{symbol + r.nextInt(100)}
    }
}