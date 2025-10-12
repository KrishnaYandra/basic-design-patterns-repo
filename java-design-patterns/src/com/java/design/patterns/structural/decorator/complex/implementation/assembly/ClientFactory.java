package com.java.design.patterns.structural.decorator.complex.implementation.assembly;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.concrete.component.BasicHttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.CircuitBreakerDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.MetricsDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.RetryDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.TracingDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.time.Duration;

public class ClientFactory {

    public static HttpClient buildObservableClient() {
        HttpClient base = new BasicHttpClient();

        HttpClient withRetry = new RetryDecorator(
                base,
                3,                      // attempts
                Duration.ofMillis(100), // base backoff
                Duration.ofSeconds(1)   // max backoff
        );

        HttpClient withBreaker = new CircuitBreakerDecorator(
                withRetry,
                10,            // window size
                0.5,           // open if >=50% failures in window
                Duration.ofSeconds(3), // cool-down in OPEN
                3,             // half-open probes
                0.67           // need >=67% success to close
        );

        MetricsDecorator.MetricsRegistry registry = new MetricsDecorator.MetricsRegistry();
        HttpClient withMetrics = new MetricsDecorator(withBreaker, registry);

        HttpClient withTracing = new TracingDecorator(withMetrics);
        return new InstrumentedClient(withTracing, registry);
    }

    // Small helper to expose metrics to callers while keeping HttpClient API
    public static final class InstrumentedClient implements HttpClient {
        private final HttpClient client;
        private final MetricsDecorator.MetricsRegistry metrics;

        public InstrumentedClient(HttpClient client, MetricsDecorator.MetricsRegistry metrics) {
            this.client = client;
            this.metrics = metrics;
        }

        @Override
        public HttpResponse execute(HttpRequest request) throws java.io.IOException, HttpClientException {
            return client.execute(request);
        }

        public MetricsDecorator.MetricsRegistry metrics() { return metrics; }
    }
}
