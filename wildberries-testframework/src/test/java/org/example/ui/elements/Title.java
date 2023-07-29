package org.example.ui.elements;

public class Title extends BaseElement {
    public Title(String elementName, String locator) {
        super(elementName, locator);
    }
    public String getText() {
        return getSelenideElement().getText();
    }
}
