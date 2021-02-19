package com.ag.simcapi3.handler

import com.ag.simcapi3.repo.ResultRepo
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class SimHandler (resultRepo: ResultRepo){
    val resultRepo = resultRepo;

    fun getResults(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(resultRepo.findAll()))
                .switchIfEmpty(ServerResponse.notFound()
                        .build())

    }

    fun getTest(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("test"))
                .switchIfEmpty(ServerResponse.notFound()
                        .build())

    }


}