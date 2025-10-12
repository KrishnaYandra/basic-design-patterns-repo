package com.java.design.patterns.structural.decorator.complex.implementation.model;

import java.net.URI;
import java.util.*;

public final class HttpRequest {
    private final String method;
    private final URI uri;
    private final Map<String, String> headers;
    private final byte[] body;

    private HttpRequest(String method, URI uri, Map<String, String> headers, byte[] body) {
        this.method = method;
        this.uri = uri;
        this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(headers));
        this.body = body == null ? null : Arrays.copyOf(body, body.length);
    }

    public String method() { return method; }
    public URI uri() { return uri; }
    public Map<String, String> headers() { return headers; }
    public Optional<byte[]> body() { return Optional.ofNullable(body == null ? null : Arrays.copyOf(body, body.length)); }

    public static Builder newBuilder() { return new Builder(); }

    public static final class Builder {
        private String method = "GET";
        private URI uri = URI.create("http://localhost");
        private final Map<String, String> headers = new LinkedHashMap<>();
        private byte[] body;

        public Builder method(String method) {
            this.method = Objects.requireNonNull(method);
            return this;
        }

        public Builder uri(URI uri) {
            this.uri = Objects.requireNonNull(uri);
            return this;
        }

        public Builder header(String name, String value) {
            headers.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
            return this;
        }

        public Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() { return new HttpRequest(method, uri, headers, body); }
    }
}