package com.ag.simcapi3.service

import com.ag.simcapi3.model.QueueSimulation
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration

@Service
class QueueService {
    var queue: ArrayList<QueueSimulation> = ArrayList()

    fun getQueuePosition(uuid: String): Int {
        val queuedSimulation = queue.firstOrNull { it.UUID.equals(uuid) }
        val simIndex = queue.indexOf(queuedSimulation)

        if (queuedSimulation != null) {
            return simIndex
        } else
            return -1

    }

    fun queuePositionStream(uuid: String): Flux<Int>{
        return Flux
                .interval(Duration.ofSeconds(2))
                .map { getQueuePosition(uuid) }
    }
}