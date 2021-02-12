package com.ag.simcapi3

import com.ag.simcapi3.model.Message
import com.ag.simcapi3.model.QueueSimulation
import com.ag.simcapi3.model.SimResult
import com.ag.simcapi3.repo.ResultRepo
import com.ag.simcapi3.service.SimWorker
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class SimController (val simWorker: SimWorker, val resultRepo: ResultRepo){

//    @PostMapping(value = ["/simulate"], consumes = ["application/json"], produces = ["application/json"])
//    fun simulate(@RequestBody profile: Profile): SimResult {
//        return simService.runSim(profile.profile)
//    }
//
//    @PostMapping(value = ["/simulate-armory"], consumes = ["application/json"], produces = ["application/json"])
//    fun simulateArmory(@RequestBody profile: ArmoryInfo): SimResult {
//        return simService.simArmory(profile)
//    }
//
//    @GetMapping(value = ["/simulate-armory"], produces = ["application/json"])
//    fun simulateArmory(@RequestParam(value = "region") region: String,
//                       @RequestParam(value = "realm") realm: String,
//                       @RequestParam(value = "charName") charName: String
//    ): SimResult {
//        return simService.simArmory2(ArmoryInfo(region, realm, charName))
//    }

    @PostMapping(value = ["/queuesim"], consumes = ["application/json"], produces = ["application/json"])
    fun placeInQueue(@RequestBody queueSimulation: QueueSimulation): Message {


        val simulationQueue = simWorker.queue

        simulationQueue.add(queueSimulation)

        println("added to queue: " + queueSimulation.UUID)
        println("queue size" + simulationQueue.size)
        println("index of queue" + simulationQueue.indexOf(queueSimulation))

        return Message("queued", "${simulationQueue.size}", "${simulationQueue.indexOf(queueSimulation)}")
    }

    @GetMapping(value = ["/placeinqueue"], produces = ["application/json"])
    fun getPlaceInQueue(@RequestParam(value = "uuid") uuid: String): QueueSimulation? {

        val queuedSimulations = simWorker.queue

        val queuedSimulation = queuedSimulations.firstOrNull { it.UUID.equals(uuid) }

        if (queuedSimulation != null) {
            return queuedSimulation
        } else
            return null

    }

    @GetMapping(value = ["/getresult"], produces = ["application/json"])
    fun getResult(@RequestParam(value = "uuid") uuid: String): SimResult? {
        var result = resultRepo.findById(uuid)
        return result.get()
    }

    @GetMapping(value = ["/getallresult"], produces = ["application/json"])
    fun getAllResult(@RequestParam(value = "uuid") uuid: String): MutableIterable<SimResult> {
        var result = resultRepo.findAll()
        return result
    }
}