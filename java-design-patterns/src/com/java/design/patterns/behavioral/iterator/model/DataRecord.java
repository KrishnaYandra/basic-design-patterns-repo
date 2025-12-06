package com.java.design.patterns.behavioral.iterator.model;

import java.util.HashMap;
import java.util.Map;

public class DataRecord {
    private String id;
    private String source;
    private String content;
    private long timestamp;
    private Map<String, Object> metadata;

    public DataRecord(String id, String source, String content, long timestamp) {
        this.id = id;
        this.source = source;
        this.content = content;
        this.timestamp = timestamp;
        this.metadata = new HashMap<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getSource() { return source; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
    public Map<String, Object> getMetadata() { return metadata; }
    
    public void addMetadata(String key, Object value) {
        metadata.put(key, value);
    }

    @Override
    public String toString() {
        return String.format("DataRecord[id=%s, source=%s, content=%s, timestamp=%d]", 
            id, source, content, timestamp);
    }
}
