package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Price extends BaseElement {
    public Price(String elementName, String locator) {
        super(elementName, locator);
    }

    public Integer getPrice() {
        SelenideElement element = getSelenideElement();
        String price = element.getOwnText().replaceAll("\\D", "");
        return Integer.parseInt(price);
    }
}
