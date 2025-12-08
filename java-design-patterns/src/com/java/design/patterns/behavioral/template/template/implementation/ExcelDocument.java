package com.java.design.patterns.behavioral.template.template.implementation;

import com.java.design.patterns.behavioral.template.template.DocumentTemplate;

public class ExcelDocument extends DocumentTemplate {
    @Override
    protected void loadData() {
        System.out.println("Loading data for Excel...");
    }

    @Override
    protected void formatDocument() {
        System.out.println("Formatting Excel content...");
    }

    @Override
    protected void exportDocument() {
        System.out.println("Exporting as Excel file.");
    }
}
