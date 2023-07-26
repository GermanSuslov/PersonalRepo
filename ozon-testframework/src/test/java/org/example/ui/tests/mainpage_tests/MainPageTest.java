package org.example.ui.tests.mainpage_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainPageTest extends BaseTest {
    private MainPage mainPage;
    @Before
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Owner("German")
    @DisplayName("Catalog button test")
    @Description("Description annotation test")
    @Test
    public void test1() {
        mainPage.catalogBtnClick();
        Assert.assertTrue(mainPage.catalogMenuIsVisible());
    }
}
