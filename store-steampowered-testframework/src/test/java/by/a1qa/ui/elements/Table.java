package by.a1qa.ui.elements;

import by.a1qa.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Table extends BaseElement {
    public Table(String elementName, By locator) {
        super(elementName, locator);
    }

    public List<String> getElementsText() {
        return DriverUtils.findElements(getLocator())
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}