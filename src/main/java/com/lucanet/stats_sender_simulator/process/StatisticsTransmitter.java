package com.lucanet.stats_sender_simulator.process;

import java.io.IOException;
import java.time.Clock;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class StatisticsTransmitter {
    
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final String aggregatorURL;
    private final OkHttpClient httpClient;
    
    public StatisticsTransmitter(String aggregatorURL) {
        this.aggregatorURL = aggregatorURL;
        this.httpClient = new OkHttpClient();
    }
    
    public void transmitStatistics(String computerUUID, JSONObject statistics) {
        long timestamp = Clock.systemUTC().instant().getEpochSecond();
        System.out.println(String.format("Sending stats for %s at %d: %s", computerUUID, timestamp, statistics.toString()));
        StringBuilder urlBuilder = new StringBuilder(aggregatorURL);
        urlBuilder.append("/stats/").append(computerUUID).append("/upload_statistics");
        HttpUrl url = HttpUrl.parse(urlBuilder.toString()).newBuilder().addQueryParameter("timestamp", Long.toString(timestamp)).build();
        RequestBody body = RequestBody.create(MEDIA_TYPE, statistics.toString());
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-type", "application/json")
                .post(body)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            System.out.println(String.format("Response for %s at %d: %d", computerUUID, timestamp, response.code()));
        } catch (IOException ioe) {
            System.err.println(String.format("Connection error for %s at %d: %s", computerUUID, timestamp, ioe.getMessage()));
        }
    }
}
