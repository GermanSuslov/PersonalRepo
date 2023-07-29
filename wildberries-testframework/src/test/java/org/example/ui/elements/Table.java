package org.example.ui.elements;

public class Table extends BaseElement {
    public Table(String elementName, String locator) {
        super(elementName, locator);
    }

    public boolean isHidden() {
        return !getSelenideElement().isDisplayed();
    }
}
