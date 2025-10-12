package com.java.design.patterns.structural.decorator.complex.implementation.exception;

public class HttpClientException extends Exception {
    public HttpClientException(String message) { super(message); }
    public HttpClientException(String message, Throwable cause) { super(message, cause); }
}