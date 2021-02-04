package com.ag.simcapi3.service


import com.ag.simcapi3.model.ArmoryInfo
import com.ag.simcapi3.model.SimResult
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime
import java.util.*


@Service
class SimService () {

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

        var resultString = output.readText();
        var dps = resultString.substringAfter("DPS Ranking:\r\n   ").substringBefore(" ")
        var time = LocalDateTime.now()


        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")
        return SimResult(resultString, dps ,time)
    }

    fun simArmory(armory: ArmoryInfo): SimResult {
        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)

        val commands1 = "armory=" + armory.region + "," + armory.realm + "," + armory.charName
        val commands2  = "save=C:\\Users\\albin\\simc-api-3\\src\\main\\resources\\profiles\\" + newUUID + ".simc"
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", commands1, commands2)
        val process = pb.start()
        var ret = process.waitFor();

        val output = File("src/main/resources/results/$newUUID.txt")
        val pb2 = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb2.redirectOutput(output)
        val process2 = pb2.start()
        var ret2 = process2.waitFor();

        var time = LocalDateTime.now()
        var resultString = output.readText();
        var dps = resultString.substringAfter("DPS Ranking:\r\n   ").substringBefore(" ")

        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")
        return SimResult(resultString, dps ,time)
    }



}