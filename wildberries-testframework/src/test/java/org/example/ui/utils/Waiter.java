package org.example.ui.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import org.example.ui.elements.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class Waiter {
    private static final Logger logger = LoggerFactory.getLogger(Waiter.class);
    private static Waiter waiter;

    private Waiter() {
    }

    public static Waiter getWaiter() {
        if (waiter == null) {
            waiter = new Waiter();
        }
        return waiter;
    }

    public void waitForElementToBeClickable(BaseElement element) {
        waitUntil(element, Condition.visible);
    }

    public void waitForElementToBeVisible(BaseElement element) {
        waitUntil(element, Condition.visible);
    }
    public void waitForElementToBeInvisible(BaseElement element) {
        waitUntil(element, Condition.hidden);
    }

    public void waitForElementsToBeVisible(List<SelenideElement> elements) {
        for (SelenideElement element : elements) {
            element.shouldBe(Condition.visible, Duration.ofSeconds(DataHelper.getWaitTime()));
        }
    }

    private void waitUntil(BaseElement element, Condition condition) {
        try {
            element.getSelenideElement()
                    .shouldBe(condition, Duration.ofSeconds(DataHelper.getWaitTime()));
        } catch (UIAssertionError e) {
            logger.error("Can not find '" + element.getElementName() +
                    "' condition \nby locator : " + element.getLocator());
        }
    }

    public static void quitWaiter() {
        waiter = null;
    }
}
