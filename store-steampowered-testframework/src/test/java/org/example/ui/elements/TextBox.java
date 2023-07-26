package org.example.ui.elements;

import org.openqa.selenium.By;

public class TextBox extends BaseElement {
    public TextBox(String elementName, By locator) {
        super(elementName, locator);
    }

    public boolean containsText(String text) {
        return getElement().getText().contains(text);
    }

    public String getText() {
        return getElement().getText();
    }

    public boolean isEmpty() {
        return getElement().getText().isEmpty();
    }
}