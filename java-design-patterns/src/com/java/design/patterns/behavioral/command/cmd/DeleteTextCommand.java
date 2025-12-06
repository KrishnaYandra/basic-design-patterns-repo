package com.java.design.patterns.behavioral.command.cmd;

import com.java.design.patterns.behavioral.command.receiver.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteTextCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteTextCommand.class);
    private TextDocument document;
    private int position;
    private int length;
    private String deletedText;

    public DeleteTextCommand(TextDocument document, int position, int length) {
        this.document = document;
        this.position = position;
        this.length = length;
        logger.debug("DeleteTextCommand created for pos={} length={}", position, length);
    }

    @Override
    public void execute() {
        logger.info("Executing DeleteTextCommand at position {} length={}", position, length);
        // Save deleted text to restore in undo
        deletedText = document.getText().substring(position, position + length);
        document.delete(position, length);
        logger.debug("Document after delete: {}", document.getText());
    }

    @Override
    public void undo() {
        logger.info("Undoing DeleteTextCommand at position {} restoring='{}'", position, deletedText);
        document.insert(position, deletedText);
        logger.debug("Document after undo delete: {}", document.getText());
    }
}
