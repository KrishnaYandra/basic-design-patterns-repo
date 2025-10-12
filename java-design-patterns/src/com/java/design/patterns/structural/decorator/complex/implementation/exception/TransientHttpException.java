package com.java.design.patterns.structural.decorator.complex.implementation.exception;

public class TransientHttpException extends HttpClientException {
    public TransientHttpException(String message, Throwable cause) { super(message, cause); }
}