package org.example.ui.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.ui.elements.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class Browser {

    public static void openUrl(String url) {
        Selenide.open(url);
    }

    public static SelenideElement findElementByXpath(String locator) {
        return $(By.xpath(locator));
    }

    public static List<SelenideElement> findElements(String locator) {
        List<SelenideElement> elementList = $$(By.xpath(locator));
        return elementList;
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

    public static void moveToElement(SelenideElement element) {
        actions().moveToElement(element).perform();
        actions().moveToElement(element).perform();
    }
}
