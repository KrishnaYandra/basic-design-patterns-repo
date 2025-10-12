package com.java.design.patterns.structural.decorator.complex.implementation.assembly;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.CircuitBreakerDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.MetricsDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.RetryDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete.TracingDecorator;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.time.Duration;

// FluentBuilder to chain decorators
public final class HttpClientChain {
    private HttpClient current;

    private HttpClientChain(HttpClient base) {
        this.current = base;
    }

    public static HttpClientChain of(HttpClient base) {
        return new HttpClientChain(base);
    }

    public HttpClientChain retry(int maxAttempts, Duration baseBackoff, Duration maxBackoff) {
        this.current = new RetryDecorator(this.current, maxAttempts, baseBackoff, maxBackoff);
        return this;
    }

    public HttpClientChain circuitBreaker(
            int windowSize,
            double failureRateToOpen,
            Duration openStateDuration,
            int halfOpenMaxCalls,
            double halfOpenSuccessRateToClose
    ) {
        this.current = new CircuitBreakerDecorator(
                this.current, windowSize, failureRateToOpen, openStateDuration,
                halfOpenMaxCalls, halfOpenSuccessRateToClose
        );
        return this;
    }

    public HttpClientChain metrics(MetricsDecorator.MetricsRegistry registry) {
        this.current = new MetricsDecorator(this.current, registry);
        return this;
    }

    public HttpClientChain tracing() {
        this.current = new TracingDecorator(this.current);
        return this;
    }

    // If you want to end with an HttpClient to execute manually:
    public HttpClient build() {
        return this.current;
    }

    // Convenience: execute directly from the chain (optional)
    public HttpResponse execute(HttpRequest request) throws IOException, HttpClientException {
        return this.current.execute(request);
    }
}
