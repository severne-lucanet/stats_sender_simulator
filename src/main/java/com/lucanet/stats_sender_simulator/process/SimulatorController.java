package com.lucanet.stats_sender_simulator.process;

import com.severett.stats_sender_simulator.model.ComputerSimulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class SimulatorController {
    
    private static TimerTask wrapTimerTask(Runnable r) {
        return new TimerTask() {

            @Override
            public void run() {
                r.run();
            }
        };
    }

    private final StatisticsTransmitter statisticsTransmitter;
    private final List<ComputerSimulation> simulationList;
    private final boolean sendJSON;
    
    public SimulatorController(String aggregatorURL, int simulationNumber, boolean sendJSON) {
        this.statisticsTransmitter = new StatisticsTransmitter(aggregatorURL);
        this.simulationList = new ArrayList<>();
        IntStream.range(0, simulationNumber).forEach(i -> simulationList.add(new ComputerSimulation()));
        this.sendJSON = sendJSON;
    }
    
    public void start() {
        simulationList.forEach(simulation -> {
            new Timer().schedule(wrapTimerTask(() -> {
                if (sendJSON) {
                    statisticsTransmitter.transmitJSONStatistics(simulation.getComputerUUID(), simulation.generateStatisticsJSON());
                } else {
                    statisticsTransmitter.transmitProtobufStatistics(simulation.generateStatisticsObj());
                }
            }), 50L, 1000L);
            try {
                Thread.sleep(50L);
            } catch (InterruptedException ex) {
                //No-op
            }
        });
    }
    
}
