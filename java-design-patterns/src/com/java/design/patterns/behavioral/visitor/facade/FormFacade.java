package com.java.design.patterns.behavioral.visitor.facade;

import com.java.design.patterns.behavioral.visitor.element.Form;
import com.java.design.patterns.behavioral.visitor.visitor.implementation.*;

public class FormFacade {
    private final SubmitFormOperation submitFormOperation;
    private final ArchiveFormOperation archiveFormOperation;
    
    public FormFacade(SubmitFormOperation submitFormOperation,
                     ArchiveFormOperation archiveFormOperation) {
        this.submitFormOperation = submitFormOperation;
        this.archiveFormOperation = archiveFormOperation;
    }
    
    public void submit(Form form) {
        form.doOperation(submitFormOperation);
    }
    
    public void archive(Form form) {
        form.doOperation(archiveFormOperation);
    }
}
