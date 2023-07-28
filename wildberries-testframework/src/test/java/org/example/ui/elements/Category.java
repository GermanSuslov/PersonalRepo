package org.example.ui.elements;

import org.openqa.selenium.By;

public class Category extends BaseElement {
    public Category(String elementName, By locator) {
        super(elementName, locator);
    }

    public void setCategory(String category) {
        String xpath = getLocator().toString().replace("By.xpath: ", "");
        String locator = String.format(xpath, category);
        setLocator(By.xpath(locator));
    }
}
