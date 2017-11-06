package com.severett.stats_sender_simulator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.json.JSONObject;

public class ComputerSimulation {

    private static final List<String> OPERATING_SYSTEMS = Arrays.asList("Windows 8", "Windows 10");
    private static final List<String> PRODUCT_VERSIONS = Arrays.asList("9.2", "10/Facelift 1", "10 Facelift 2", "10 Facelift 3", "11 Preview 1", "11");
    
    private final String computerUUID;
    private final String operatingSystem;
    private final String productVersion;
    private final int memoryCapacity;
    
    public ComputerSimulation() {
        this.computerUUID = UUID.randomUUID().toString();
        this.operatingSystem = OPERATING_SYSTEMS.get(ThreadLocalRandom.current().nextInt(OPERATING_SYSTEMS.size()));
        this.productVersion = PRODUCT_VERSIONS.get(ThreadLocalRandom.current().nextInt(PRODUCT_VERSIONS.size()));
        this.memoryCapacity = ThreadLocalRandom.current().nextInt(1000000, 1000000000);
    }
    
    public String getComputerUUID() {
        return computerUUID;
    }
    
    public JSONObject generateStatistics() {
        JSONObject stats = new JSONObject();
        stats.put("operatingSystem", operatingSystem);
        stats.put("productVersion", productVersion);
        int systemCPULoad = ThreadLocalRandom.current().nextInt(100, 10000);
        int processCPULoad = ThreadLocalRandom.current().nextInt(100, systemCPULoad);
        stats.put("processCPULoad", new BigDecimal((double) processCPULoad / 100).setScale(2, RoundingMode.HALF_UP));
        stats.put("systemCPULoad", new BigDecimal((double) systemCPULoad / 100).setScale(2, RoundingMode.HALF_UP));
        stats.put("memoryUsage", ThreadLocalRandom.current().nextInt(10000, memoryCapacity + 1));
        stats.put("memoryCapacity", memoryCapacity);
        return stats;
    }
    
}
