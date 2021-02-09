package com.ag.simcapi3

import com.ag.simcapi3.model.CompletedSimulations
import com.ag.simcapi3.model.QueueSimulation
import com.ag.simcapi3.model.SimResult
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime
import kotlin.properties.Delegates

@Service
class SimWorker(): Runnable {

    var queue: ArrayList<QueueSimulation> by Delegates.notNull()
    var completedSimulations: ArrayList<CompletedSimulations> by Delegates.notNull()

    init {
        this.queue = ArrayList()
        this.completedSimulations = ArrayList()
        val t: Unit = Thread(this).start()
    }




    override fun run() {
        println("worker started")
            while(true) {
                    if(queue.size > 0 && queue != null) {
                        val firstInQueue: QueueSimulation = queue.get(0)
                        val fileName = "src/main/resources/profiles/${firstInQueue.UUID}.simc"
                        val profileFile = File(fileName)
                        profileFile.writeText(firstInQueue.profile)

                        val output = File("src/main/resources/results/${firstInQueue.UUID}.txt")
                        val pb = ProcessBuilder("src/main/resources/simc-902-01-win64/simc.exe", profileFile.toString())
                        pb.redirectOutput(output)
                        val process = pb.start()
                        val ret = process.waitFor()

                        val resultString = output.readText()
                        val dps = resultString.substringAfter("DPS Ranking:").substringBefore("%").trim().substringBefore(" ")
                        val time = LocalDateTime.now()

                        Thread.sleep(10000)

                        profileFile.delete()
                        output.delete()

                        println("Simulation of ${firstInQueue.UUID} resulted in $dps")
                        completedSimulations.add(CompletedSimulations(firstInQueue.UUID, SimResult(resultString,dps, LocalDateTime.now())))

                        queue.remove(firstInQueue);
                    }
            }
        }

}