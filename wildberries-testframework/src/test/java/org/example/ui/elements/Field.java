package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.utils.Browser;

public class Field extends BaseElement {
    public Field(String elementName, String locator) {
        super(elementName, locator);
    }

    public void enterText(String text) {
        Browser.enterText(getSelenideElement(), text);
    }

    public void clearField() {
        SelenideElement selenideElement = getSelenideElement();
        Browser.clearField(selenideElement);
        if(!selenideElement.getValue().isEmpty()) {
            Browser.clearField(selenideElement);
        }
    }
}
