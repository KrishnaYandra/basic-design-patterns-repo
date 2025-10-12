package com.java.design.patterns.structural.decorator.complex.implementation.decorator.concrete;

import com.java.design.patterns.structural.decorator.complex.implementation.HttpClient;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.exception.PermanentHttpException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class CircuitBreakerDecorator implements HttpClient {
    enum State { CLOSED, OPEN, HALF_OPEN }

    private final HttpClient delegate;
    private final int windowSize;              // number of recent calls
    private final double failureRateToOpen;    // e.g., 0.5 => 50%
    private final Duration openStateDuration;  // cool-down
    private final int halfOpenMaxCalls;        // allowed probes
    private final double halfOpenSuccessRateToClose; // success ratio to close

    private State state = State.CLOSED;
    private Instant stateSince = Instant.now();

    private final Deque<Boolean> outcomes = new ArrayDeque<>();
    private int halfOpenCalls = 0;
    private int halfOpenSuccess = 0;

    public CircuitBreakerDecorator(HttpClient delegate,
                                   int windowSize,
                                   double failureRateToOpen,
                                   Duration openStateDuration,
                                   int halfOpenMaxCalls,
                                   double halfOpenSuccessRateToClose) {
        this.delegate = Objects.requireNonNull(delegate);
        this.windowSize = windowSize;
        this.failureRateToOpen = failureRateToOpen;
        this.openStateDuration = openStateDuration;
        this.halfOpenMaxCalls = halfOpenMaxCalls;
        this.halfOpenSuccessRateToClose = halfOpenSuccessRateToClose;
    }

    @Override
    public HttpResponse execute(HttpRequest request) throws IOException, HttpClientException {
        transitionIfNeeded();
        switch (state) {
            case OPEN -> throw new PermanentHttpException("Circuit open");
            case HALF_OPEN -> {
                return halfOpenCall(request);
            }
            case CLOSED -> {
                HttpResponse resp = proceed(request);
                recordOutcome(isSuccess(resp), null);
                maybeOpen();
                return resp;
            }
            default -> throw new IllegalStateException("Unknown state");
        }
    }

    private HttpResponse halfOpenCall(HttpRequest request) throws IOException, HttpClientException {
        halfOpenCalls++;
        try {
            HttpResponse resp = delegate.execute(request);
            boolean ok = isSuccess(resp);
            if (ok) halfOpenSuccess++;
            decideHalfOpenTransition();
            return resp;
        } catch (IOException | HttpClientException e) {
            decideHalfOpenFailure();
            throw e;
        }
    }

    private HttpResponse proceed(HttpRequest request) throws IOException, HttpClientException {
        try {
            HttpResponse resp = delegate.execute(request);
            return resp;
        } catch (IOException | HttpClientException e) {
            recordOutcome(false, e);
            maybeOpen();
            throw e;
        }
    }

    private void transitionIfNeeded() {
        if (state == State.OPEN) {
            if (Instant.now().isAfter(stateSince.plus(openStateDuration))) {
                // move to HALF_OPEN
                state = State.HALF_OPEN;
                stateSince = Instant.now();
                halfOpenCalls = 0;
                halfOpenSuccess = 0;
            }
        }
    }

    private void decideHalfOpenTransition() {
        if (halfOpenCalls >= halfOpenMaxCalls) {
            double successRate = halfOpenCalls == 0 ? 0 : (double) halfOpenSuccess / halfOpenCalls;
            if (successRate >= halfOpenSuccessRateToClose) {
                // close circuit
                state = State.CLOSED;
                stateSince = Instant.now();
                outcomes.clear();
            } else {
                // re-open
                state = State.OPEN;
                stateSince = Instant.now();
            }
        }
    }

    private void decideHalfOpenFailure() {
        // immediate reopen on a failure during half-open
        state = State.OPEN;
        stateSince = Instant.now();
    }

    private void recordOutcome(boolean success, Exception e) {
        outcomes.addLast(success);
        if (outcomes.size() > windowSize) outcomes.removeFirst();
    }

    private void maybeOpen() {
        if (state != State.CLOSED) return;
        if (outcomes.size() < windowSize) return; // need enough data points
        long failures = outcomes.stream().filter(b -> !b).count();
        double rate = (double) failures / outcomes.size();
        if (rate >= failureRateToOpen) {
            state = State.OPEN;
            stateSince = Instant.now();
        }
    }

    private boolean isSuccess(HttpResponse resp) {
        return resp.is2xx();
    }
}
