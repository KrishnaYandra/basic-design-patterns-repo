package com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.PermanentHttpException;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.TransientHttpException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.util.Objects;

public class MetricsDecorator implements HttpClient {
    private final HttpClient delegate;
    private final MetricsRegistry metrics;

    public MetricsDecorator(HttpClient delegate, MetricsRegistry metrics) {
        this.delegate = Objects.requireNonNull(delegate);
        this.metrics = Objects.requireNonNull(metrics);
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, HttpClientException {
        long start = System.nanoTime();
        try {
            HttpResponse resp = delegate.execute(request);
            long durNs = System.nanoTime() - start;
            if (resp.is2xx()) {
                metrics.recordSuccess(durNs);
            } else if (resp.is5xx()) {
                metrics.recordServerError(durNs);
            } else {
                metrics.recordClientError(durNs);
            }
            return resp;
        } catch (TransientHttpException e) {
            long durNs = System.nanoTime() - start;
            metrics.recordTransientFailure(durNs);
            throw e;
        } catch (PermanentHttpException e) {
            long durNs = System.nanoTime() - start;
            metrics.recordPermanentFailure(durNs);
            throw e;
        } catch (IOException e) {
            long durNs = System.nanoTime() - start;
            metrics.recordIoFailure(durNs);
            throw e;
        }
    }

    public static class MetricsRegistry {
        private long successCount;
        private long clientErrorCount;
        private long serverErrorCount;
        private long transientFailureCount;
        private long permanentFailureCount;
        private long ioFailureCount;

        private long totalLatencyNs;
        private long maxLatencyNs;

        public synchronized void recordSuccess(long durNs) { successCount++; addLatency(durNs); }
        public synchronized void recordClientError(long durNs) { clientErrorCount++; addLatency(durNs); }
        public synchronized void recordServerError(long durNs) { serverErrorCount++; addLatency(durNs); }
        public synchronized void recordTransientFailure(long durNs) { transientFailureCount++; addLatency(durNs); }
        public synchronized void recordPermanentFailure(long durNs) { permanentFailureCount++; addLatency(durNs); }
        public synchronized void recordIoFailure(long durNs) { ioFailureCount++; addLatency(durNs); }

        private void addLatency(long durNs) {
            totalLatencyNs += durNs;
            if (durNs > maxLatencyNs) maxLatencyNs = durNs;
        }

        @Override
        public synchronized String toString() {
            long totalCount = successCount + clientErrorCount + serverErrorCount
                    + transientFailureCount + permanentFailureCount + ioFailureCount;
            long avgMs = totalCount == 0 ? 0 : (totalLatencyNs / totalCount) / 1_000_000;
            long maxMs = maxLatencyNs / 1_000_000;
            return "Metrics{" +
                    "success=" + successCount +
                    ", clientErr=" + clientErrorCount +
                    ", serverErr=" + serverErrorCount +
                    ", transientFail=" + transientFailureCount +
                    ", permanentFail=" + permanentFailureCount +
                    ", ioFail=" + ioFailureCount +
                    ", avgMs=" + avgMs +
                    ", maxMs=" + maxMs +
                    '}';
        }
    }
}
