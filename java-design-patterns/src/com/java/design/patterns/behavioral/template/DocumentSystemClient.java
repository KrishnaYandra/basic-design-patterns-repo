package com.java.design.patterns.behavioral.template;

import com.java.design.patterns.behavioral.template.template.DocumentTemplate;
import com.java.design.patterns.behavioral.template.template.implementation.ExcelDocument;
import com.java.design.patterns.behavioral.template.template.implementation.PdfDocument;

public class DocumentSystemClient {
    public static void main(String[] args) {
        DocumentTemplate doc = new PdfDocument();
        doc.generateDocument();

        System.out.println("------");

        doc = new ExcelDocument();
        doc.generateDocument();
    }
}
