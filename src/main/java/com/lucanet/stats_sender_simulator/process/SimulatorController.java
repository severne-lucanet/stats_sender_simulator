package com.lucanet.stats_sender_simulator.process;

import com.severett.stats_sender_simulator.model.ComputerSimulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class SimulatorController {

    private final StatisticsTransmitter statisticsTransmitter;
    private final List<ComputerSimulation> simulationList;
    
    public SimulatorController(String aggregatorURL, int simulationNumber) {
        this.statisticsTransmitter = new StatisticsTransmitter(aggregatorURL);
        this.simulationList = new ArrayList<>();
        IntStream.range(0, simulationNumber).forEach(i -> simulationList.add(new ComputerSimulation()));
    }
    
    public void start() {
        simulationList.forEach(simulation -> {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    statisticsTransmitter.transmitStatistics(simulation.getComputerUUID(), simulation.generateStatistics());
                }
            }, 50L, 1000L);
            try {
                Thread.sleep(50L);
            } catch (InterruptedException ex) {
                //No-op
            }
        });
    }
    
}
