package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.example.ui.utils.Browser;

@Getter
public class Category extends BaseElement {
    private Category categoryElement;
    public Category(String elementName, String locator) {
        super(elementName, locator);
    }

    public void setCategory(String categoryName) {
        String categoryLocator = String.format(getLocator(), categoryName);
        categoryElement = new Category(categoryName, categoryLocator);
    }

    @Override
    public void click() {
        categoryElement.getSelenideElement().click();
    }
}
