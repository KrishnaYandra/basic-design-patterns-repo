package com.java.design.patterns.behavioral.visitor.visitor;

import com.java.design.patterns.behavioral.visitor.element.implementation.*;

public interface FormOperation {
    void doOperation(BasicIncidentData basicIncidentData);
    void doOperation(BasicInfoAboutCar basicInfoAboutCar);
    void doOperation(PolicySelectionForm policySelectionForm);
    // 37 more overloaded methods for remaining form types
}
