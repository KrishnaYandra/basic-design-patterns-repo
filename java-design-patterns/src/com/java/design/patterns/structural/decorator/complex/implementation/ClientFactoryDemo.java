package com.java.design.patterns.structural.decorator.complex.implementation;

import com.java.design.patterns.structural.decorator.complex.implementation.assembly.ClientFactory;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.net.URI;

public class ClientFactoryDemo {
    public static void main(String[] args) throws Exception {
        ClientFactory.InstrumentedClient client =
                (ClientFactory.InstrumentedClient) ClientFactory.buildObservableClient();

        for (int i = 0; i < 25; i++) {
            HttpRequest req = HttpRequest.newBuilder()
                    .method("GET")
                    .uri(URI.create("http://service.local/api/resource?i=" + i))
                    .header("Accept", "application/json")
                    .build();
            try {
                HttpResponse resp = client.execute(req);
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
