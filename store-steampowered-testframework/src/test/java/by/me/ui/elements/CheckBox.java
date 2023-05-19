package by.me.ui.elements;

import by.me.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckBox extends BaseElement {
    public CheckBox(String elementName, By locator) {
        super(elementName, locator);
    }

    public void clickAll() {
        List<WebElement> checkBoxList;
        while ((checkBoxList = DriverUtils.findElements(getLocator())).size() != 0) {
            checkBoxList
                    .forEach(WebElement::click);
        }
    }
}
