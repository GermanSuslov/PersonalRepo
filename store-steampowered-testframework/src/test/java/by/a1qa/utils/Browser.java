package by.a1qa.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Browser {
    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(Browser.class);

    public static WebDriver getDriver() {
        if (driver == null) {
            String browserType = ResourcesHelper.getBrowserType();
            switch (browserType) {
                case "CHROME":
                    driver = getChromeDriver();
                    break;
                case "FIREFOX":
                    driver = getFirefoxDriver();
                    break;
                case "SAFARI":
                    driver = getSafariDriver();
                    break;
                case "EDGE":
                    driver = getEdgeDriver();
                    break;
                default:
                    driver = null;
                    logger.warn("Browser '" + browserType + "' is not supported");
            }
        }
        return driver;
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        ResourcesHelper.getDriverOptions().forEach(options::addArguments);
        driver = new ChromeDriver(options);
        return driver;
    }

    private static WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        ResourcesHelper.getDriverOptions().forEach(options::addArguments);
        driver = new FirefoxDriver(options);
        return driver;
    }

    private static WebDriver getSafariDriver() {
        WebDriverManager.safaridriver().setup();
        SafariOptions options = new SafariOptions();
        driver = new SafariDriver(options);
        return driver;
    }

    private static WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        ResourcesHelper.getDriverOptions().forEach(options::addArguments);
        driver = new EdgeDriver(options);
        return driver;
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}