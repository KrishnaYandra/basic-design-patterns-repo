package com.java.design.patterns.structural.decorator.complex.implementation;

import com.java.design.patterns.structural.decorator.complex.implementation.exception.HttpClientException;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpRequest;
import com.java.design.patterns.structural.decorator.complex.implementation.model.HttpResponse;

import java.io.IOException;
import java.util.*;

public interface HttpClient {
    HttpResponse execute(HttpRequest request) throws IOException, HttpClientException;
}