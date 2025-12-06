package com.java.design.patterns.behavioral.command;

import com.java.design.patterns.behavioral.command.invoker.*;
import com.java.design.patterns.behavioral.command.receiver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Starting Command pattern demo (Main)");
        TextDocument document = new TextDocument();
        CommandHistory history = new CommandHistory();
        EditorController editor = new EditorController(document, history);

        editor.insertText(0, "Hello");
        logger.info("After insert 1: {}", editor.getText()); // Hello

        editor.insertText(5, " World");
        logger.info("After insert 2: {}", editor.getText()); // Hello World

        editor.deleteText(5, 6);
        logger.info("After delete: {}", editor.getText()); // Hello

        editor.undo();
        logger.info("After undo 1: {}", editor.getText()); // Hello World

        editor.undo();
        logger.info("After undo 2: {}", editor.getText()); // Hello

        editor.redo();
        logger.info("After redo: {}", editor.getText()); // Hello World
        logger.info("Command pattern demo finished");
    }
}
