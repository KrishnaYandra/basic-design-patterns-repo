package com.java.design.patterns.behavioral.command.receiver;

public class TextDocument {
    private StringBuilder text = new StringBuilder();

    public void insert(int position, String content) {
        text.insert(position, content);
    }

    public void delete(int position, int length) {
        text.delete(position, position + length);
    }

    public String getText() {
        return text.toString();
    }
}
