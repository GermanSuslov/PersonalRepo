package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.example.ui.utils.Browser;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
public abstract class BaseElement {
    private static final Logger logger = LoggerFactory.getLogger(BaseElement.class);
    private String elementName;
    private String locator;

    public BaseElement(String elementName, String locator) {
        this.elementName = elementName;
        this.locator = locator;
    }

    public boolean isDisplayed() {
        return getSelenideElement().isDisplayed();
    }

    public boolean areDisplayed() {
        boolean areDisplayed = false;
        if (getElements().stream().filter(SelenideElement::isDisplayed).toList().size() > 0) {
            areDisplayed = true;
        }
        return areDisplayed;
    }

    public boolean isEnabled() {
        return getSelenideElement().isEnabled();
    }

    public void click() {
        try {
            getSelenideElement().click();
        } catch (NoSuchElementException e) {
            logger.error("Can not click element - '" + elementName + "' \nby locator " + locator);
        }
    }

    public void moveToElement() {
        Browser.moveToElement(getSelenideElement());
    }

    public SelenideElement getSelenideElement() {
        SelenideElement element = null;
        try {
            element = Browser.findElementByXpath(locator);
        } catch (NoSuchElementException e) {
            logger.error("Can not find element - '" + elementName + "' \nby locator " + locator);
        }
        return element;
    }

    public List<SelenideElement> getElements() {
        List<SelenideElement> element = null;
        try {
            element = Browser.findElements(locator);
        } catch (NoSuchElementException e) {
            logger.error("Can not find element - '" + elementName + "' \nby locator " + locator);
        }
        return element;
    }
}
