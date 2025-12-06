package com.java.design.patterns.behavioral.state.exception;

public class InvalidStateTransitionException extends RuntimeException {
    public InvalidStateTransitionException(String s) {
        super(s);
    }
}
