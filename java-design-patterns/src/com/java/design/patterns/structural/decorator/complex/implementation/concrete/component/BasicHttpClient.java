package com.java.design.patterns.structural.decorator.complex.implementation.concrete.component;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.TransientHttpException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BasicHttpClient implements HttpClient {

    @Override
    public HttpResponse execute(HttpRequest request) throws HttpClientException {
        // Simulate real I/O latency
        sleep(30, 80);

        // Simulate outcomes:
        int r = ThreadLocalRandom.current().nextInt(100);
        if (r < 10) {
            // Simulate network error (retryable)
            throw new TransientHttpException("Network glitch", new IOException("ECONNRESET"));
        } else if (r < 20) {
            // Simulate server 5xx (retryable)
            return new HttpResponse(503, Map.of("Date", Instant.now().toString()), "Service Unavailable".getBytes());
        } else if (r < 25) {
            // Simulate client 4xx (non-retryable)
            return new HttpResponse(400, Map.of(), "Bad Request".getBytes());
        }

        // Success
        return new HttpResponse(200, Map.of("Date", Instant.now().toString()), "OK".getBytes());
    }

    private static void sleep(int minMs, int maxMs) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minMs, maxMs + 1));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}