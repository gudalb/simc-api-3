package com.ag.simcapi3

import com.ag.simcapi3.model.ArmoryInfo
import com.ag.simcapi3.model.Profile
import com.ag.simcapi3.model.SimResult
import com.ag.simcapi3.service.SimService
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class SimController (val simService: SimService){

    @PostMapping(value = ["/simulate"], consumes = ["application/json"], produces = ["application/json"])
    fun simulate(@RequestBody profile: Profile): SimResult {
        return simService.runSim(profile.profile)
    }

    @PostMapping(value = ["/simulate-armory"], consumes = ["application/json"], produces = ["application/json"])
    fun simulateArmory(@RequestBody profile: ArmoryInfo): SimResult {
        return simService.simArmory(profile)
    }

    @GetMapping("/simulateTest")
    fun greeting2(): String {
        return "test"
        //return SimResult(name, LocalDateTime.now())
    }

}