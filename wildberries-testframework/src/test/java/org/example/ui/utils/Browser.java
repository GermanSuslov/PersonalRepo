package org.example.ui.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.ui.elements.BaseElement;

import java.util.List;

public class Browser {
    private static WebDriver webDriver;
    private Actions actions = new Actions(Selenide.webdriver().driver().getWebDriver());

    public static void openUrl(String url) {
        Selenide.open(url);
    }

    public static SelenideElement findElement(By locator) {
        return Selenide.element(locator);
    }

    public static List<SelenideElement> findElements(By locator) {
        return Selenide.elements(locator);
    }

    public static WebDriver getDriver() {
        if (webDriver == null) {
            webDriver = Selenide.webdriver().driver().getWebDriver();
        }
        return webDriver;
    }

    public static void setBrowser() {
        String browserType = DataHelper.getBrowserType();
        switch (browserType) {
            case "CHROME":
                setChromeBrowser();
                break;
            case "FIREFOX":
                setFirefoxBrowser();
                break;
            case "SAFARI":
                setSafariBrowser();
                break;
            case "EDGE":
                setEdgeBrowser();
                break;
            default:
                System.out.println("Браузер не указан или не поддерживается");
                ;
        }
    }

    private static void setChromeBrowser() {
        ChromeOptions options = new ChromeOptions();
        DataHelper.getBrowserOptions().forEach(options::addArguments);
        Configuration.browserCapabilities = options;
        Configuration.browserSize = DataHelper.getBrowserSize();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    private static void setFirefoxBrowser() {
    }

    private static void setSafariBrowser() {
    }

    private static void setEdgeBrowser() {
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

    public static void moveToElement(BaseElement element) {

    }
}
