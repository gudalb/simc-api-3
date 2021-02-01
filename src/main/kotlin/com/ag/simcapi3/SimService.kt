package com.ag.simcapi3


import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Service
class SimService {

    fun runSim(profile: String): SimResult {
        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)
        profileFile.writeText(profile)

        val output = File("src/main/resources/results/$newUUID.txt")
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb.redirectOutput(output)
        val process = pb.start()
        var ret = process.waitFor();

        println("Simulation results saved to " + output.toString())

        return SimResult(output.readText(), LocalDateTime.now())
    }



}