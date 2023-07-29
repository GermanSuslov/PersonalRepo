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

    public List<Integer> getPrice() {
        List<SelenideElement> elementList = getElements();
        List<Integer> priceList = new ArrayList<>();
        for (SelenideElement element : elementList) {
            System.out.println(element.getText());
            priceList.add(Integer.parseInt(element.getText()));
        }
        return priceList;
    }
}
