package com.java.design.patterns.structural.decorator.complex.implementation.model;

import java.util.*;

public final class HttpResponse {
    private final int statusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    public HttpResponse(int statusCode, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(headers));
        this.body = body == null ? null : Arrays.copyOf(body, body.length);
    }

    public int statusCode() { return statusCode; }
    public Map<String, String> headers() { return headers; }
    public Optional<byte[]> body() { return Optional.ofNullable(body == null ? null : Arrays.copyOf(body, body.length)); }

    public boolean is2xx() { return statusCode >= 200 && statusCode < 300; }
    public boolean is5xx() { return statusCode >= 500 && statusCode < 600; }
}