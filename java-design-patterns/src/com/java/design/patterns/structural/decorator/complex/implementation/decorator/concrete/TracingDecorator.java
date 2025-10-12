package com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TracingDecorator implements HttpClient {
    private final HttpClient delegate;

    public static final String CORRELATION_ID = "X-Correlation-Id";

    public TracingDecorator(HttpClient delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, HttpClientException {
        String corrId = request.headers().getOrDefault(CORRELATION_ID, UUID.randomUUID().toString());
        HttpRequest tracedReq = ensureHeader(request, CORRELATION_ID, corrId);

        long start = System.nanoTime();
        log("START " + tracedReq.method() + " " + tracedReq.uri() + " corrId=" + corrId);

        try {
            HttpResponse resp = delegate.execute(tracedReq);
            long durMs = Duration.ofNanos(System.nanoTime() - start).toMillis();
            log("END   status=" + resp.statusCode() + " corrId=" + corrId + " durMs=" + durMs);
            return resp;
        } catch (Exception e) {
            long durMs = Duration.ofNanos(System.nanoTime() - start).toMillis();
            log("ERROR " + e.getClass().getSimpleName() + " corrId=" + corrId + " durMs=" + durMs + " msg=" + e.getMessage());
            throw e;
        }
    }

    private HttpRequest ensureHeader(HttpRequest req, String name, String value) {
        if (value.equals(req.headers().get(name))) return req;
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .method(req.method())
                .uri(req.uri());
        req.headers().forEach(b::header);
        b.header(name, value);
        req.body().ifPresent(b::body);
        return b.build();
    }

    private void log(String s) {
        System.out.println("[TRACE] " + s);
    }
}
