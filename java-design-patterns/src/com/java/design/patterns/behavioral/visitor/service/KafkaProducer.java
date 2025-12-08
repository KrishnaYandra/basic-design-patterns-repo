package com.java.design.patterns.behavioral.visitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    // topic -> list of messages
    private final Map<String, List<String>> sentMessages = new HashMap<>();

    public synchronized void send(String topic, String message) {
        sentMessages
            .computeIfAbsent(topic, t -> new ArrayList<>())
            .add(message);

        // Just log instead of sending to a real broker
        logger.info("Dummy Kafka send - topic='{}', message='{}'", topic, message);
    }

    // Helper for tests / debugging
    public List<String> getMessages(String topic) {
        return sentMessages.getOrDefault(topic, List.of());
    }
}
