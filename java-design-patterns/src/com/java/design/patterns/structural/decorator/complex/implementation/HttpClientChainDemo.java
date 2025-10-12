package com.java.design.patterns.structural.decorator.complex.implementation;

import com.java.design.patterns.structural.decorator.complex.implementation.assembly.ClientFactory;
import com.java.design.patterns.structural.decorator.complex.implementation.assembly.HttpClientChain;
import com.java.design.patterns.structural.decorator.complex.implementation.concrete.component.BasicHttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.MetricsDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.net.URI;
import java.time.Duration;

public class HttpClientChainDemo {
    public static void main(String[] args) throws Exception {
        ClientFactory.InstrumentedClient client =
                (ClientFactory.InstrumentedClient) ClientFactory.buildObservableClient();

        for (int i = 0; i < 25; i++) {
            try {
                HttpResponse resp = HttpClientChain.of(new BasicHttpClient())
                        .retry(3, Duration.ofMillis(100), Duration.ofSeconds(1))
                        .circuitBreaker(10, 0.5, Duration.ofSeconds(3), 3, 0.67)
                        .metrics(new MetricsDecorator.MetricsRegistry())
                        .tracing()
                        .execute(HttpRequest.newBuilder()
                                .method("GET")
                                .uri(URI.create("http://service.local/health"))
                                .build());

                System.out.println("Response: " + resp.statusCode() + " body=" +
                        resp.body().map(b -> new String(b)).orElse("<empty>"));
            } catch (HttpClientException | java.io.IOException e) {
                System.out.println("Call failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }

            Thread.sleep(150); // pacing
        }

        System.out.println(client.metrics().toString());
    }
}
