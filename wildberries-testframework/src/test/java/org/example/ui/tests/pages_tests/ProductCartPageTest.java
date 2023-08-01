package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.CategoryPage;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.forms.pages.ProductCartPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductCartPageTest extends BaseTest {
    private MainPage mainPage;

    @Before
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Owner("German Suslov")
    @DisplayName("Наличие характеристики \"Состав\" на карточке товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Красота\" - \"Для загара\" - Страница \"Средства защиты от солнца и загара\" открыта\n" +
            "3. Выбрать первую карточку товара - Страница первой карточки товара открыта\n" +
            "4. Просмотреть информацию о товаре - \"Состав\" присутствует")
    @Test
    public void productStructureTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Красота");
        mainPage.headerForm.catalogForm.innerCategoryClick("Для загара");
        CategoryPage categoryPage = new CategoryPage("Средства защиты от солнца и загара");
        Assert.assertTrue("Страница категории \"Средства защиты от солнца и загара\" не открыта",
                categoryPage.isOpen());

        categoryPage.productCartClick(1);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        Assert.assertTrue("Карточка товара не содержит слово \"Состав\"",
                productCartPage.containsInformation("Состав"));
        Assert.assertTrue("Карточка товара не содержит слово \"ВОДА\"",
                productCartPage.containsInformation("ВОДА"));
    }

    @Owner("German Suslov")
    @DisplayName("Наличие характеристики \"Производитель\" и \"Габариты\" на карточке товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Дом\" - \"Гостиная\" - \"Мебель\" - Страница \"Мебель\" открыта\n" +
            "3. Выбрать первую карточку товара - Страница первой карточки товара открыта\n" +
            "4. Развернуть все описания, изучить информацию о товаре - " +
            "\"Страна производства\" присутствует, раздел \"Габариты\" присутствует")
    @Test
    public void aboutProductTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Дом");
        mainPage.headerForm.catalogForm.innerCategoryClick("Гостиная");
        mainPage.headerForm.catalogForm.innerCategoryClick("Мебель");
        CategoryPage categoryPage = new CategoryPage("Мебель");
        Assert.assertTrue("Страница категории \"Мебель\" не открыта",
                categoryPage.isOpen());

        categoryPage.productCartClick(1);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        Assert.assertTrue("Карточка товара не содержит слово \"Страна производства\"",
                productCartPage.containsInformation("Страна производства"));
        Assert.assertTrue("Карточка товара не содержит слово \"Габариты\"",
                productCartPage.containsInformation("Габариты"));
    }
}
