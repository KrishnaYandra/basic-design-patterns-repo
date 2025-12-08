package com.java.design.patterns.behavioral.template.template.implementation;

import com.java.design.patterns.behavioral.template.template.DocumentTemplate;

public class PdfDocument extends DocumentTemplate {
    @Override
    protected void loadData() {
        System.out.println("Loading data for PDF...");
    }

    @Override
    protected void formatDocument() {
        System.out.println("Formatting PDF content...");
    }

    @Override
    protected void exportDocument() {
        System.out.println("Exporting as PDF file.");
    }
}
