package com.ag.simcapi3

import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class SimController (val simService: SimService){

    @PostMapping(value = ["/simulate"], consumes = ["application/json"], produces = ["application/json"])
    fun greeting(@RequestBody profile: Profile): SimResult {
        return simService.runSim(profile.profile)
    }

    @GetMapping("/simulateTest")
    fun greeting2(): SimResult {
        return simService.runSim("test")
        //return SimResult(name, LocalDateTime.now())
    }

}