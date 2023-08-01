package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.CategoryPage;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.forms.pages.SearchResultPage;
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

    @Owner("German Suslov")
    @DisplayName("Открытие и закрытие каталога на главной странице")
    @Description("1. Зайти на главную страницу Wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом исчезло.")
    @Test
    public void catalogButtonTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.catalogForm.catalogMenuIsVisible());

        mainPage.catalogForm.closeCatalogBtnClick();
        Assert.assertTrue("Каталог отображен", mainPage.catalogForm.catalogMenuIsHidden());
    }

    @Owner("German Suslov")
    @DisplayName("Переход на страницу \"Мужские тапочки\" с помощью каталога")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Обувь\" - \"Мужская\" - \"Тапочки\"  - Страница \"Мужские тапочки\" открыта")
    @Test
    public void catalogCategoryTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.catalogForm.catalogMenuIsVisible());

        mainPage.catalogForm.mainCategoryMove("Обувь");
        mainPage.catalogForm.innerCategoryClick("Мужская");
        mainPage.catalogForm.innerCategoryClick("Тапочки");
        CategoryPage tapochkiPage = new CategoryPage("Мужские тапочки");
        Assert.assertTrue("Страница категории \"Мужские тапочки\" не открыта", tapochkiPage.isOpen());
    }

    @Owner("German Suslov")
    @DisplayName("Переход на главную страницу со страници категории \"Джинсы для мальчиков\" с помощью кнопки главной страницы")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Детям\" - \"Для мальчиков\" - \"Джинсы\"  - Страница \"Джинсы для мальчиков\" открыта\n" +
            "4. Нажать на кнопку главной страницы - Главная страница открыта")
    @Test
    public void mainPageButtonTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.catalogForm.catalogMenuIsVisible());

        mainPage.catalogForm.mainCategoryMove("Детям");
        mainPage.catalogForm.innerCategoryClick("Для мальчиков");
        mainPage.catalogForm.innerCategoryClick("Джинсы");
        CategoryPage jeansPage = new CategoryPage("Джинсы для мальчиков");
        Assert.assertTrue("Страница категории \"Джинсы для мальчиков\" не открыта", jeansPage.isOpen());

        mainPage.catalogForm.mainPageButtonClick();
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
    }

    @Owner("German Suslov")
    @DisplayName("Количество карточек с промоакциями на главной странице больше 10")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Посчитать количество карточек с промоакциями - Количество карточек с промоакциями больше 10")
    @Test
    public void promoCardsAmountTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        Integer promoCartsAmount = mainPage.getPromoCardsAmount();
        Assert.assertTrue("Количество карточек с промоакциями меньше 10", promoCartsAmount > 10);
    }

    @Owner("German Suslov")
    @DisplayName("Поиск по запросу \"кофемашина\" с главной страницы")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Ввести в строку поиска слово \"кофемашина\", нажать Enter - " +
            "Страница с результатом поиска \"кофемашина\" открыта\n")
    @Test
    public void searchTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        String searchPhrase = "кофемашина";
        mainPage.searchForm.search(searchPhrase);
        SearchResultPage searchResultPage = new SearchResultPage(searchPhrase);
        Assert.assertTrue("Страница с результатом поиска \"кофемашина\" не открыта", searchResultPage.isOpen());
    }
}
