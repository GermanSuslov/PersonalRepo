package by.a1qa.ui.tests;

import by.a1qa.utils.Browser;
import by.a1qa.utils.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;

public class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @AfterMethod
    public void tearDown() {
        Waiter.quitWaiter();
        Browser.quitDriver();
    }
}