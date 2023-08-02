package org.example.ui.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import org.example.ui.elements.BaseElement;
import org.example.ui.elements.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.closeWebDriver;

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
    public void waitForFieldToBeEmpty(Field field) {
        waitUntil(field, Condition.empty);
    }
    public void waitForElementToBeInvisible(BaseElement element) {
        waitUntil(element, Condition.hidden);
    }

    public void waitForElementsToBeVisible(List<SelenideElement> elements) {
        for (SelenideElement element : elements) {
            element.shouldBe(Condition.visible, Duration.ofSeconds(DataHelper.getWaitTime()));
        }
    }

    public void waitByMillis(Integer millis) {
        try {
            Selenide.sleep(millis);
        } catch (Exception e) {
            logger.error("Ошибка implicit wait " + e);
        }
    }

    private void waitUntil(BaseElement element, Condition condition) {
        try {
            element.getSelenideElement()
                    .shouldBe(condition, Duration.ofSeconds(DataHelper.getWaitTime()));
        } catch (UIAssertionError e) {
            logger.error("Элемент - '" + element.getElementName() +
                    "' не соответствует ожидаемому состоянию '" + condition + "' \nby locator : " +
                    element.getLocator() + "\nException: \n" + e);
            tearDown();
        } catch (Exception ex) {
            logger.error("Element " + element.getElementName() + " not found by locator "
                    + element.getLocator());
            tearDown();
        }
    }

    private void tearDown() {
        quitWaiter();
        closeWebDriver();
    }

    public static void quitWaiter() {
        waiter = null;
    }
}
