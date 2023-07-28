package org.example.ui.tests.mainpage_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPageTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MainPageTest.class);
    private MainPage mainPage;

    @Before
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Owner("German Suslov")
    @DisplayName("Открытие и закрытие каталога на главной странице")
    @Description("1. Зайти на главную страницу Wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом исчезло.")
    @Test
    public void catalogButtonTest() {
        logger.info("catalogButtonTest начался");
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.catalogForm.catalogMenuIsVisible());

        mainPage.catalogForm.closeCatalogBtnClick();
        Assert.assertTrue("Каталог отображен", mainPage.catalogForm.catalogMenuIsHidden());
        logger.info("catalogButtonTest закончен");
    }

    @Owner("German Suslov")
    @DisplayName("Соответствие процента скидки ценам на карточке товара в \"Лучшие предложения!\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Обувь\" - \"Мужская\" - \"Тапочки\"  - Страница \"Мужские тапочки\" открыта")
    @Test
    public void bestDealsDiscountTest() {
        logger.info("catalogButtonTest начался");
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        logger.info("bestDealsDiscountTest начался");
        mainPage.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.catalogForm.catalogMenuIsVisible());

        mainPage.catalogForm.mainCategoryClick("Обувь");
        Assert.assertTrue("Страница ", mainPage.isOpen());

        Assert.assertTrue("Цены и скидка не отображены", mainPage.firstBestDealsPriceIsVisible());
    }
}
