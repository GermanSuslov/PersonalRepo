package org.example.ui.elements;

import org.openqa.selenium.By;

public class Table extends BaseElement {
    public Table(String elementName, By locator) {
        super(elementName, locator);
    }

    public boolean isHidden() {
        return !getElement().isDisplayed();
    }
}
