package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import org.openqa.selenium.By;

public class TopBar extends BaseElement {

    private static final By HEADER = By.cssSelector(".top-bar h1");

    public TopBar(ElementActions elementActions) {
        super(elementActions);
    }

    public boolean hasHeader(String header) {
        if (!elementActions.isVisible(HEADER)) return false;
        return elementActions.getText(HEADER).equals(header);
    }
}
