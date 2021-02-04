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

    fun simArmory(armory: ArmoryInfo): SimResult {
        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)

        val commands1 = "armory=" + armory.region + "," + armory.realm + "," + armory.charName
        val commands2  = "save=C:\\Users\\albin\\simc-api-3\\src\\main\\resources\\profiles\\" + newUUID + ".simc"


        println("running commands with processbuilder: $commands1 $commands2")

        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", commands1, commands2)
        val process = pb.start()
        var ret = process.waitFor();

        val output = File("src/main/resources/results/$newUUID.txt")
        val pb2 = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb2.redirectOutput(output)
        val process2 = pb2.start()
        var ret2 = process2.waitFor();




        println("Simulation results saved to " + output.toString())
        return SimResult(output.readText(), LocalDateTime.now())
    }



}