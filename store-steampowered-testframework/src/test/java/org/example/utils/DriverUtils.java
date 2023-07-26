package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;

public class DriverUtils {
    public static void navigate(String url) {
        Browser.getDriver().navigate().to(url);
    }

    public static WebElement findElement(By locator) {
        return Browser.getDriver().findElement(locator);
    }

    public static List<WebElement> findElements(By locator) {
        return Browser.getDriver().findElements(locator);
    }

    public static void closeRecentWindow() {
        String[] windowHandles = Browser.getDriver().getWindowHandles().toArray(new String[0]);
        String firstWindow = windowHandles[0];
        String secondWindow = windowHandles[1];
        Browser.getDriver().switchTo().window(firstWindow);
        Browser.getDriver().close();
        Browser.getDriver().switchTo().window(secondWindow);
    }

    public static int getNumberOfWindows() {
        Set<String> windowHandles = Browser.getDriver().getWindowHandles();
        return windowHandles.size();
    }

    public static void scrollPageToBottom() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) Browser.getDriver();
        long lastHeight;
        long newHeight = 0;
        do {
            lastHeight = newHeight;
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            newHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");
        } while (newHeight != lastHeight);
    }

    public static void navigateMouse(WebElement element) {
        Actions actions = new Actions(Browser.getDriver());
        actions.moveToElement(element).perform();
    }
}