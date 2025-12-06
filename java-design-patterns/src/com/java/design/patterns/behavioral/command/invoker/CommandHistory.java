package com.java.design.patterns.behavioral.command.invoker;

import com.java.design.patterns.behavioral.command.cmd.Command;

import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHistory {
    private static final Logger logger = LoggerFactory.getLogger(CommandHistory.class);
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void executeCommand(Command command) {
        logger.info("Executing command: {}", command.getClass().getSimpleName());
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        logger.debug("Undo stack size: {}, Redo stack cleared", undoStack.size());
    }

    public void undo() {
        logger.info("Attempting undo");
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            logger.info("Undoing command: {}", command.getClass().getSimpleName());
            command.undo();
            redoStack.push(command);
            logger.debug("Undo completed. Undo size: {}, Redo size: {}", undoStack.size(), redoStack.size());
        } else {
            logger.info("Nothing to undo");
        }
    }

    public void redo() {
        logger.info("Attempting redo");
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            logger.info("Redoing command: {}", command.getClass().getSimpleName());
            command.execute();
            undoStack.push(command);
            logger.debug("Redo completed. Undo size: {}, Redo size: {}", undoStack.size(), redoStack.size());
        } else {
            logger.info("Nothing to redo");
        }
    }
}
