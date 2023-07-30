package org.example.ui.elements;

public class Number extends BaseElement {
    public Number(String elementName, String locator) {
        super(elementName, locator);
    }

    public Integer getNumber() {
        String number = getSelenideElement().getText();
        number = number.replaceAll("\\D", "");
        return Integer.parseInt(number);
    }

}
