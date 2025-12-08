package com.java.design.patterns.behavioral.template.template;

public abstract class DocumentTemplate {
    public final void generateDocument() {
        loadData();
        formatDocument();
        exportDocument();
        logCompletion();
    }

    protected abstract void loadData();

    protected abstract void formatDocument();

    protected abstract void exportDocument();

    protected void logCompletion() {
        System.out.println("Document generation completed.");
    }
}
