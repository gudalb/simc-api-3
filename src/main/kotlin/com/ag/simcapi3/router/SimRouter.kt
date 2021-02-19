package com.ag.simcapi3.router

import com.ag.simcapi3.handler.SimHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*


@Configuration
class SimRouter(private val simHandler: SimHandler) {
//    @Bean
//    fun UserRoutes(simHandler: SimHandler): RouterFunction<ServerResponse> {
//        return RouterFunctions.route().path("/sim"
//        ) { builder: RouterFunctions.Builder ->
//            builder.GET("", RequestPredicates.accept(MediaType.APPLICATION_JSON), simHandler::getResults)
//        }
//                .build()
//    }

    @Bean
    fun SimRoutes() = router {
        (accept(MediaType.TEXT_HTML) and "/sim").nest {
            GET("", simHandler::getResults)
            GET("/test", simHandler::getTest)
        }
    }


}
