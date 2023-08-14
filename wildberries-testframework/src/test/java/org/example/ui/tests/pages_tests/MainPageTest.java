package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.CategoryPage;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.forms.pages.ProductCardPage;
import org.example.ui.forms.pages.SearchResultPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Feature("Тесты главной страницы")
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

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.closeCatalogBtnClick();
        Assert.assertTrue("Каталог отображен", mainPage.headerForm.catalogForm.catalogMenuIsHidden());
    }

    @Owner("German Suslov")
    @DisplayName("Переход на страницу \"Мужские тапочки\" с помощью каталога")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Обувь\" - \"Мужская\" - \"Тапочки\"  - Страница \"Мужские тапочки\" открыта")
    @Test
    public void catalogCategoryTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Обувь");
        mainPage.headerForm.catalogForm.innerCategoryClick("Мужская");
        mainPage.headerForm.catalogForm.innerCategoryClick("Тапочки");
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

        mainPage.headerForm.catalogForm.catalogBtnClick();
        Assert.assertTrue("Каталог не отображен", mainPage.headerForm.catalogForm.catalogMenuIsVisible());

        mainPage.headerForm.catalogForm.mainCategoryMove("Детям");
        mainPage.headerForm.catalogForm.innerCategoryClick("Для мальчиков");
        mainPage.headerForm.catalogForm.innerCategoryClick("Джинсы");
        CategoryPage jeansPage = new CategoryPage("Джинсы для мальчиков");
        Assert.assertTrue("Страница категории \"Джинсы для мальчиков\" не открыта", jeansPage.isOpen());

        mainPage.headerForm.catalogForm.mainPageButtonClick();
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
        mainPage.headerForm.searchForm.search(searchPhrase);
        SearchResultPage searchResultPage = new SearchResultPage(searchPhrase);
        Assert.assertTrue("Страница с результатом поиска \"кофемашина\" не открыта", searchResultPage.isOpen());
    }

    @Owner("German Suslov")
    @DisplayName("Наличие маркетингового текста внизу главной страницы")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Прокуртить страницу доконца вниз - " +
            "Маркетинговый текст отображен, содержит слова: \"Wildberries\", \"Интернет-магазин\"\n")
    @Test
    public void mainPageTextTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        Assert.assertTrue("Маркетинговый текст не отображен", mainPage.mainPageTextIsDisplayed());
        Assert.assertTrue("Маркетинговый текст не содержит слово \"Wildberries\"",
                mainPage.containsInformation("Wildberries"));
        Assert.assertTrue("Маркетинговый текст не содержит слово \"Интернет-магазин\"",
                mainPage.containsInformation("Интернет-магазин"));
    }

    @Owner("German Suslov")
    @DisplayName("Поиск по запросу \"футболка\" и переход на первую карточку товара из результатов с главной страницы")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Ввести в строку поиска слово \"футболка\", нажать Enter - " +
            "Страница с результатом поиска \"футболка\" открыта\n" +
            "3. Нажать на первую карточку товара из результатов - " +
            "Страница с карточкой товара \"футболка\" открыта\n")
    @Test
    public void cardSearchTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        String searchPhrase = "футболка";
        mainPage.headerForm.searchForm.search(searchPhrase);
        SearchResultPage searchResultPage = new SearchResultPage(searchPhrase);
        Assert.assertTrue("Страница с результатом поиска \"футболка\" не открыта", searchResultPage.isOpen());

        searchResultPage.productCartClick(1);
        ProductCardPage productCardPage = new ProductCardPage();
        Assert.assertTrue("Страница карточки товара не открыта", productCardPage.isOpen());
    }
}
