package org.example.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.ui.utils.Browser;
import org.example.ui.utils.Waiter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeOptions;
import static com.codeborne.selenide.Selenide.closeWebDriver;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    @BeforeClass
    public static void setUpAll() {
        Browser.setBrowser();
    }
    @After
    public void tearDown() {
        Waiter.quitWaiter();
        closeWebDriver();
    }
}

/*
* ChromeOptions options = new ChromeOptions().setHeadless(true);
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.geolocation", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-dev-shm-usage");
        Configuration.headless = false;
        Configuration.browserSize = "720x460";
        Configuration.browserPosition = "0x0";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }*/
