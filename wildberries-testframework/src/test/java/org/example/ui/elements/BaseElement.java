package org.example.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.example.ui.utils.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class BaseElement {
    private static final Logger logger = LoggerFactory.getLogger(BaseElement.class);
    private String elementName;
    private By locator;

    public BaseElement(String elementName, By locator) {
        this.elementName = elementName;
        this.locator = locator;
    }

    public boolean isDisplayed() {
        return getElement().isDisplayed();
    }
    public boolean areDisplayed() {
        boolean areDisplayed = false;
        if(getElements().stream().filter(SelenideElement::isDisplayed).toList().size() > 0) {
            areDisplayed = true;
        }
        return areDisplayed;
    }

    public boolean isEnabled() {
        return getElement().isEnabled();
    }

    public void click() {
        getElement().click();
    }
    public void move() {
        Browser.;
    }
    public void setLocator(By locator) {
        this.locator = locator;
    }

    public SelenideElement getElement() {
        SelenideElement element = null;
        try {
            element = Browser.findElement(locator);
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
