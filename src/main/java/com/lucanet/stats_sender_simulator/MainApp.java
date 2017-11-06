package com.lucanet.stats_sender_simulator;

import com.lucanet.stats_sender_simulator.process.SimulatorController;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainApp {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("a", "aggregatorURL", true, "URL of the stats aggregator");
        options.addOption("n", "number", true, "Number of computer simulations");
        options.addOption("p", "protobuf", false, "Use Protobuf instead of JSON");
        options.addOption("h", "help", false, "Print this message");
        CommandLineParser cliParser = new DefaultParser();
        try {
            CommandLine cmd = cliParser.parse(options, args);
            if ((cmd.hasOption("h")) || (!cmd.hasOption("a") || !cmd.hasOption("n"))) {
                new HelpFormatter().printHelp("MainApp -a <URL> -n <NUMBER>", options);
            } else {
                boolean useJSON = !(cmd.hasOption("p"));
                SimulatorController simulatorController = new SimulatorController(cmd.getOptionValue("a"), Integer.parseInt(cmd.getOptionValue("n")), useJSON);
                simulatorController.start();
            }
        } catch (ParseException pe) {
            System.err.println(String.format("Error parsing options for simulator: %s", pe.getMessage()));
            new HelpFormatter().printHelp("MainApp -a <URL> -n <NUMBER>", options);
        }
    }
    
}
