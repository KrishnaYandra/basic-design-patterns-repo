package com.java.design.patterns.behavioral.visitor.element;

import com.java.design.patterns.behavioral.visitor.visitor.FormOperation;

public interface Form {
    void doOperation(FormOperation formOperation);
}
