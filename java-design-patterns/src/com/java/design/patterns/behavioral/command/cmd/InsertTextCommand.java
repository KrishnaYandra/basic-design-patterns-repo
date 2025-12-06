package com.java.design.patterns.behavioral.command.cmd;

import com.java.design.patterns.behavioral.command.receiver.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertTextCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(InsertTextCommand.class);
    private TextDocument document;
    private String textToInsert;
    private int position;

    public InsertTextCommand(TextDocument document, int position, String textToInsert) {
        this.document = document;
        this.position = position;
        this.textToInsert = textToInsert;
        logger.debug("InsertTextCommand created for pos={} text='{}'", position, textToInsert);
    }

    @Override
    public void execute() {
        logger.info("Executing InsertTextCommand at position {} with text='{}'", position, textToInsert);
        document.insert(position, textToInsert);
        logger.debug("Document after insert: {}", document.getText());
    }

    @Override
    public void undo() {
        logger.info("Undoing InsertTextCommand at position {} with length={}", position, textToInsert.length());
        document.delete(position, textToInsert.length());
        logger.debug("Document after undo insert: {}", document.getText());
    }
}
