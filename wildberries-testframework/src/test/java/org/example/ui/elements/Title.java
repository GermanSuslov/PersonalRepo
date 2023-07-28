package org.example.ui.elements;

import org.openqa.selenium.By;

public class Title extends BaseElement {
    public Title(String elementName, By locator) {
        super(elementName, locator);
    }
    public String getText() {
        return getElement().getText();
    }
}
