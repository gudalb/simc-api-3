package com.ag.simcapi3

import com.ag.simcapi3.model.Message
import com.ag.simcapi3.model.QueueSimulation
import com.ag.simcapi3.model.SimResult
import com.ag.simcapi3.repo.ResultRepo
import com.ag.simcapi3.service.QueueService
import com.ag.simcapi3.service.SimWorker
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class SimController (val simWorker: SimWorker, val resultRepo: ResultRepo, val queueService: QueueService){

    @PostMapping(value = ["/queuesim"], consumes = ["application/json"], produces = ["application/json"])
    fun placeInQueue(@RequestBody queueSimulation: QueueSimulation): Int {


        val simulationQueue = queueService.queue

        simulationQueue.add(queueSimulation)

        println("added to queue: " + queueSimulation.UUID)
        println("queue size" + simulationQueue.size)
        println("index of queue" + queueService.getQueuePosition(queueSimulation.UUID))

        return queueService.getQueuePosition(queueSimulation.UUID)
    }

    @GetMapping(value = ["/queueposition"], produces = ["application/json"])
    fun getPlaceInQueue(@RequestParam(value = "uuid") uuid: String) = queueService.getQueuePosition(uuid)

    @GetMapping(value = ["/getresult"], produces = ["application/json"])
    fun getResult(@RequestParam(value = "uuid") uuid: String): SimResult {
        var result = resultRepo.findById(uuid)
        return result.get()
    }

    @GetMapping(value = ["/getallresult"], produces = ["application/json"])
    fun getAllResult(): MutableIterable<SimResult> {
        var result = resultRepo.findAll()
        return result
    }

    @GetMapping(value = ["/queueposition/{uuid}"],produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun  queuePositionStream(@PathVariable uuid: String) = queueService.queuePositionStream(uuid)

}