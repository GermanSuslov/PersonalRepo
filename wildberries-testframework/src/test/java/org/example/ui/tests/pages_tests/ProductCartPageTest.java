package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.*;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Feature("Тесты карточек товаров")
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
            "3. Выбрать вторую карточку товара - Страница первой карточки товара открыта\n" +
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

        categoryPage.productCartClick(2);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        Assert.assertTrue("Карточка товара не содержит слово \"Состав\"",
                productCartPage.containsInformation("Состав"));
    }

    @Owner("German Suslov")
    @DisplayName("Наличие характеристики \"Производитель\" и \"Габариты\" на карточке товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Дом\" - \"Гостиная\" - \"Мебель\" - Страница \"Мебель\" открыта\n" +
            "3. Выбрать третью карточку товара - Страница первой карточки товара открыта\n" +
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

        categoryPage.productCartClick(3);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        Assert.assertTrue("Карточка товара не содержит слово \"Страна производства\"",
                productCartPage.containsInformation("Страна производства"));
        Assert.assertTrue("Карточка товара не содержит слово \"Габариты\"",
                productCartPage.containsInformation("Габариты"));
    }

    @Owner("German Suslov")
    @DisplayName("Кнопка \"Добавить в корзину\" на карточке товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Продукты\" - \"Бакалея\" - \"Крупы\" - Страница \"Крупы и макароны\" открыта\n" +
            "3. Выбрать первую карточку товара - Страница первой карточки товара открыта\n" +
            "4. Нажать кнопку \"Добавить в корзину\" - поп-ап окно об успешном добавлении в корзину появилось, " +
            "кнопка \"Добавить в корзину\" изменилась на \"Перейти в корзину\"")
    @Test
    public void buyButtonTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Продукты");
        mainPage.headerForm.catalogForm.innerCategoryClick("Бакалея");
        mainPage.headerForm.catalogForm.innerCategoryClick("Крупы");
        CategoryPage categoryPage = new CategoryPage("Крупы и макароны");
        Assert.assertTrue("Страница категории \"Крупы и макароны\" не открыта",
                categoryPage.isOpen());

        categoryPage.productCartClick(1);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        productCartPage.addToBasketButtonClick();
        Assert.assertTrue("Поп-ап окно не появилось", productCartPage.basketPopUpIsDisplayed());

        Assert.assertEquals("\"Перейти в корзину\" не отображено", "Перейти в корзину",
                productCartPage.getBuyButtonText());
    }

    @Owner("German Suslov")
    @DisplayName("Добавление в корзину со страницы карточки товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Спорт\" - \"Велоспорт\" - \"Велосипеды\" - Страница \"Велосипеды\" открыта\n" +
            "3. Выбрать первую карточку товара - Страница первой карточки товара открыта\n" +
            "4. Нажать кнопку \"Добавить в корзину\" - поп-ап окно об успешном добавлении в корзину появилось, " +
            "кнопка \"Добавить в корзину\" изменилась на \"Перейти в корзину\"\n" +
            "5. Перейти в корзину - Корзина открыта, товар \"Велосипед\" находится в корзине")
    @Test
    public void addToBasketTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Спорт");
        mainPage.headerForm.catalogForm.innerCategoryClick("Велоспорт");
        mainPage.headerForm.catalogForm.innerCategoryClick("Велосипеды");
        CategoryPage categoryPage = new CategoryPage("Велосипеды");
        Assert.assertTrue("Страница категории \"Велосипеды\" не открыта",
                categoryPage.isOpen());

        categoryPage.productCartClick(1);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        productCartPage.addToBasketButtonClick();
        Assert.assertTrue("Поп-ап окно не появилось", productCartPage.basketPopUpIsDisplayed());

        Assert.assertEquals("\"Перейти в корзину\" не отображено", "Перейти в корзину",
                productCartPage.getBuyButtonText());

        productCartPage.headerForm.basketButtonClick();
        BasketPage basketPage = new BasketPage();
        Assert.assertTrue("Корзина не открыта", basketPage.isOpen());
        Assert.assertTrue("Товар \"Велосипед\" не находится в корзине",
                basketPage.basketContainsProduct("Велосипед"));
    }

    @Owner("German Suslov")
    @DisplayName("Доступность \"Таблица размеров\" со страницы карточки товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Ввести в строку поиска слово \"брюки женские\", нажать Enter - " +
            "Страница с результатом поиска \"брюки женские\" открыта\n" +
            "3. Выбрать четвертую карточку товара - Страница четвертой карточки товара открыта\n" +
            "4. Нажать кнопку \"Таблица размеров\" - поп-ап окно с таблицей размеров появилось")
    @Test
    public void sizeTableTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        String searchPhrase = "брюки женские";
        mainPage.headerForm.searchForm.search(searchPhrase);
        SearchResultPage searchResultPage = new SearchResultPage(searchPhrase);
        Assert.assertTrue("Страница с результатом поиска \"брюки женские\" не открыта", searchResultPage.isOpen());

        searchResultPage.productCartClick(4);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());

        productCartPage.sizeTableButtonClick();
        Assert.assertTrue("Поп-ап окно с таблицей размеров не появилось",
                productCartPage.sizeTablePopUpIsDisplayed());
    }

    @Owner("German Suslov")
    @DisplayName("Переход на страницу продавца со страницы карточки товара")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Ввести в строку поиска слово \"кресло\", нажать Enter - " +
            "Страница с результатом поиска \"кресло\" открыта\n" +
            "3. Выбрать вторую карточку товара - Страница второй карточки товара открыта, имя продавца отображено\n" +
            "4. Нажать на имя продавца - Страница продавца открылась, статистика продавца отображена")
    @Test
    public void sellersButtonTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        String searchPhrase = "кресло";
        mainPage.headerForm.searchForm.search(searchPhrase);
        SearchResultPage searchResultPage = new SearchResultPage(searchPhrase);
        Assert.assertTrue("Страница с результатом поиска \"кресло\" не открыта", searchResultPage.isOpen());

        searchResultPage.productCartClick(2);
        ProductCartPage productCartPage = new ProductCartPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCartPage.isOpen());
        Assert.assertTrue("Имя продавца не отображено", productCartPage.sellerButtonIsDisplayed());

        productCartPage.sellerButtonClick();
        SellersPage sellersPage = new SellersPage();
        Assert.assertTrue("Страница продавца не открылась", sellersPage.isOpen());
        Assert.assertTrue("Статистика продавца не отображена", sellersPage.sellersInfoTablesIsDisplayed());
    }
}
