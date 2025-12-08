package com.java.design.patterns.behavioral.visitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchivalStorageService {

    private static final Logger logger = LoggerFactory.getLogger(ArchivalStorageService.class);

    // category -> list of archived records
    private final Map<String, List<String>> storage = new HashMap<>();

    public synchronized void store(String category, String payload) {
        storage.computeIfAbsent(category, c -> new ArrayList<>()).add(payload);
        logger.info("Archived record in category '{}': {}", category, payload);
    }

    // Helper method to inspect archives in tests / main()
    public List<String> getArchives(String category) {
        return storage.getOrDefault(category, List.of());
    }
}
