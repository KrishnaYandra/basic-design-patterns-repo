package com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.TransientHttpException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class RetryDecorator implements HttpClient {
    private final HttpClient delegate;
    private final int maxAttempts;
    private final Duration baseBackoff;
    private final Duration maxBackoff;

    public RetryDecorator(HttpClient delegate, int maxAttempts, Duration baseBackoff, Duration maxBackoff) {
        if (maxAttempts < 1) throw new IllegalArgumentException("maxAttempts>=1");
        this.delegate = Objects.requireNonNull(delegate);
        this.maxAttempts = maxAttempts;
        this.baseBackoff = Objects.requireNonNull(baseBackoff);
        this.maxBackoff = Objects.requireNonNull(maxBackoff);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, HttpClientException {
        int attempt = 1;
        while (true) {
            try {
                HttpResponse resp = delegate.execute(request);
                if (shouldRetry(resp) && attempt < maxAttempts) {
                    backoff(attempt);
                    attempt++;
                    continue;
                }
                return resp;
            } catch (TransientHttpException | IOException e) {
                if (attempt >= maxAttempts) {
                    if (e instanceof IOException) throw (IOException) e;
                    throw (HttpClientException) e;
                }
                backoff(attempt);
                attempt++;
            }
        }
    }

    private boolean shouldRetry(HttpResponse resp) {
        return resp.is5xx();
    }

    private void backoff(int attempt) {
        long exp = (long) Math.min(maxBackoff.toMillis(),
                baseBackoff.toMillis() * Math.pow(2, attempt - 1));
        long jitter = ThreadLocalRandom.current().nextLong(exp / 4 + 1);
        sleep(exp - jitter);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }
}