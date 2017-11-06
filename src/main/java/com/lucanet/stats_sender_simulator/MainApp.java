package com.lucanet.stats_sender_simulator;

import com.lucanet.stats_sender_simulator.process.SimulatorController;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainApp {

    public static void main(String[] args) {
        Options options = new Options();
        options.addRequiredOption("a", "aggregatorURL", true, "URL of the stats aggregator");
        options.addRequiredOption("n", "number", true, "Number of computer simulations");
        CommandLineParser cliParser = new DefaultParser();
        try {
            CommandLine cmd = cliParser.parse(options, args);
            SimulatorController simulatorController = new SimulatorController(cmd.getOptionValue("a"), Integer.getInteger(cmd.getOptionValue("n")));
            simulatorController.start();
        } catch (ParseException pe) {
            System.err.println(String.format("Error parsing options for simulator: %s", pe.getMessage()));
        }
    }
    
}
