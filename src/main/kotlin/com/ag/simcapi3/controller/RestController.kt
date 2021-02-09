package com.ag.simcapi3

import com.ag.simcapi3.model.*
import com.ag.simcapi3.service.SimService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class SimController (val simService: SimService, val simWorker: SimWorker){




    @PostMapping(value = ["/simulate"], consumes = ["application/json"], produces = ["application/json"])
    fun simulate(@RequestBody profile: Profile): SimResult {
        return simService.runSim(profile.profile)
    }

    @PostMapping(value = ["/simulate-armory"], consumes = ["application/json"], produces = ["application/json"])
    fun simulateArmory(@RequestBody profile: ArmoryInfo): SimResult {
        return simService.simArmory(profile)
    }

    @GetMapping(value = ["/simulate-armory"], produces = ["application/json"])
    fun simulateArmory(@RequestParam(value = "region") region: String,
                       @RequestParam(value = "realm") realm: String,
                       @RequestParam(value = "charName") charName: String

    ): SimResult {
        return simService.simArmory2(ArmoryInfo(region, realm, charName))
    }

    @PostMapping(value = ["/queue"], consumes = ["application/json"], produces = ["application/json"])
    fun placeInQueue(@RequestBody queueSimulation: QueueSimulation): Message {


        val simulationQueue = simWorker.queue
        val completedSimulations = simWorker.completedSimulations


        simulationQueue.add(queueSimulation)


        println("added to queue: " + queueSimulation.UUID)
        println("queue size" + simulationQueue.size)
        println("index of queue" + simulationQueue.indexOf(queueSimulation))
        println("finished sims: " + completedSimulations.size)

        return Message("queued", "${simulationQueue.size} / ${simulationQueue.indexOf(queueSimulation)}")


    }


    @GetMapping(value = ["/getResult"], produces = ["application/json"])
    fun getFromQueue(@RequestParam(value = "uuid") uuid: String): CompletedSimulations? {

        val completedSimulations = simWorker.completedSimulations

        val completedSimulation = completedSimulations.firstOrNull { it.UUID.equals(uuid) }

        if (completedSimulation != null) {
            return completedSimulation
        } else
            return null

    }
}