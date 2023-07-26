package org.example.ui.utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {
    private static WebDriver webDriver;

    public static void openUrl(String url) {
        Selenide.open(url);
    }

    public static SelenideElement findElement(By locator) {
        return Selenide.element(locator);
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
}
