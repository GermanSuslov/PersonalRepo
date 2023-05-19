package by.me.ui.elements;

import by.me.utils.DriverUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public abstract class BaseElement {
    private static final Logger logger = LoggerFactory.getLogger(BaseElement.class);
    private final String elementName;
    private final By locator;

    public BaseElement(String elementName, By locator) {
        this.elementName = elementName;
        this.locator = locator;
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }

    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    public void click() {
        getElement().click();
    }

    public WebElement getElement() {
        WebElement element = null;
        try {
            element = DriverUtils.findElement(locator);
        } catch (NoSuchElementException e) {
            logger.error("Can not find element - '" + elementName + "' \nby locator " + locator);
        }
        return element;
    }
}