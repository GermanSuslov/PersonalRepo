package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.example.ui.utils.Browser;

@Getter
public class Category extends BaseElement {
    public Category(String elementName, String locator) {
        super(elementName, locator);
    }
}
