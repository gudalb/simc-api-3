package com.ag.simcapi3.service


import com.ag.simcapi3.model.ArmoryInfo
import com.ag.simcapi3.model.CompletedSimulations
import com.ag.simcapi3.model.QueueSimulation
import com.ag.simcapi3.model.SimResult
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import java.util.*


@Service
class SimService () {

    /**
     *
     * simulates a WoW character from simc addon profile
     *
     *
     * @param profile param contains output from simc addon
     * @return simulation results
     */
    fun runSim(profile: String): SimResult {

        println(profile)

        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)
        profileFile.writeText(profile)

        val output = File("src/main/resources/results/$newUUID.txt")
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb.redirectOutput(output)
        val process = pb.start()
        val ret = process.waitFor();

        val resultString = output.readText();
        val dps = resultString.substringAfter("DPS Ranking:").substringBefore("%").trim().substringBefore(" ")
        val time = LocalDateTime.now()


        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")
        return SimResult(resultString, dps ,time)
    }

    /**
    *
    * simulates a WoW character from armory profile
    *
    *
    * @param armory param contains region, realm and character name
    * @return simulation results
     */
    fun simArmory(armory: ArmoryInfo): SimResult {
        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)
        val workingDir = getWorkingDirectory()

        val commands1 = "armory=" + armory.region + "," + armory.realm + "," + armory.charName
        val commands2  = "save=$workingDir\\src\\main\\resources\\profiles\\" + newUUID + ".simc"
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", commands1, commands2)
        val process = pb.start()
        var ret = process.waitFor();


        //val commands3 = "json=C:\\Users\\albin\\simc-api-3\\src\\main\\resources\\results\\" + newUUID + ".json"
        val output = File("src/main/resources/results/$newUUID.txt")
        val pb2 = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb2.redirectOutput(output)
        val process2 = pb2.start()
        val ret2 = process2.waitFor();

        val time = LocalDateTime.now()
        val resultString = output.readText();
        val dps = resultString.substringAfter("DPS Ranking:").substringBefore("%").trim().substringBefore(" ")

        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")
        return SimResult(resultString, dps ,time)
    }

    fun simArmory2(armory: ArmoryInfo): SimResult {
        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)
        val workingDir = getWorkingDirectory()

        val commands1 = "armory=" + armory.region + "," + armory.realm + "," + armory.charName
        val commands2  = "save=$workingDir\\src\\main\\resources\\profiles\\" + newUUID + ".simc"
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", commands1, commands2)
        val process = pb.start()
        var ret = process.waitFor();


        //val commands3 = "json=C:\\Users\\albin\\simc-api-3\\src\\main\\resources\\results\\" + newUUID + ".json"
        val output = File("src/main/resources/results/$newUUID.txt")
        val pb2 = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb2.redirectOutput(output)

        val process2 = pb2.start()
        val ret2 = process2.waitFor();

        val time = LocalDateTime.now()
        val resultString = output.readText();
        val dps = resultString.substringAfter("DPS Ranking:").substringBefore("%").trim().substringBefore(" ")

        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")

        return SimResult(resultString, dps, time)


    }

    fun getWorkingDirectory(): String {
        val directory = File("");
        return directory.absolutePath
    }

    fun runSimAuto(profile: String, queue: ArrayList<QueueSimulation>, completedSimulations: CompletedSimulations ): SimResult {

        println(profile)

        val newUUID = UUID.randomUUID()
        val fileName = "src/main/resources/profiles/$newUUID.simc"
        val profileFile = File(fileName)
        profileFile.writeText(profile)

        val output = File("src/main/resources/results/$newUUID.txt")
        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
        pb.redirectOutput(output)
        val process = pb.start()
        val ret = process.waitFor();

        val resultString = output.readText();
        val dps = resultString.substringAfter("DPS Ranking:").substringBefore("%").trim().substringBefore(" ")
        val time = LocalDateTime.now()


        profileFile.delete()
        output.delete()

        println("Simulation resulted in $dps")
        return SimResult(resultString, dps ,time)
    }


}