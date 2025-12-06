package com.java.design.patterns.behavioral.command.invoker;

import com.java.design.patterns.behavioral.command.cmd.Command;
import com.java.design.patterns.behavioral.command.cmd.DeleteTextCommand;
import com.java.design.patterns.behavioral.command.cmd.InsertTextCommand;
import com.java.design.patterns.behavioral.command.receiver.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditorController {
    private static final Logger logger = LoggerFactory.getLogger(EditorController.class);
    private TextDocument document;
    private CommandHistory history;

    public EditorController(TextDocument document, CommandHistory history) {
        this.document = document;
        this.history = history;
        logger.info("EditorController initialized");
    }

    public void insertText(int position, String text) {
        logger.info("Request to insert text at {}: '{}'", position, text);
        Command cmd = new InsertTextCommand(document, position, text);
        history.executeCommand(cmd);
        logger.debug("Text after insert: {}", document.getText());
    }

    public void deleteText(int position, int length) {
        logger.info("Request to delete text at {} (length={})", position, length);
        Command cmd = new DeleteTextCommand(document, position, length);
        history.executeCommand(cmd);
        logger.debug("Text after delete: {}", document.getText());
    }

    public void undo() {
        logger.info("Request to undo last command");
        history.undo();
        logger.debug("Text after undo: {}", document.getText());
    }

    public void redo() {
        logger.info("Request to redo");
        history.redo();
        logger.debug("Text after redo: {}", document.getText());
    }

    public String getText() {
        return document.getText();
    }
}
