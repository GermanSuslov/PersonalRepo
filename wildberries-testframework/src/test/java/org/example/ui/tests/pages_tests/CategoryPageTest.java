package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.CategoryPage;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryPageTest extends BaseTest {
    private MainPage mainPage;

    @Before
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Owner("German Suslov")
    @DisplayName("Выбор фильтра \"Nokia\" на странице \"Мобильные телефоны\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Электроника\" - \"Смартфоны и телефоны\" -" +
            " \"Мобильные телефоны\"  - Страница \"Мобильные телефоны\" открыта\n" +
            "3. Нажать кнопку \"Бренды\" - Dropdown с чекбоксами отображен.\n" +
            "4. Выбрать чекбокс \"Nokia\" - Фильтр \"Nokia\" отображен")
    @Test
    public void categoryPageFilterTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.catalogForm.catalogBtnClick();

        mainPage.catalogForm.mainCategoryMove("Электроника");
        mainPage.catalogForm.innerCategoryClick("Смартфоны и телефоны");
        mainPage.catalogForm.innerCategoryClick("Мобильные телефоны");
        CategoryPage phonesPage = new CategoryPage("Мобильные телефоны");
        Assert.assertTrue("Страница категории \"Мобильные телефоны\" не открыта", phonesPage.isOpen());

        phonesPage.filterForm.filterButtonClick("Бренд");
        Assert.assertTrue("Dropdown список не отображен", phonesPage.filterForm.isFilterDropdownOpen());

        phonesPage.filterForm.filterCheckboxClick("Nokia");
        Assert.assertTrue("Выбранный фильтр не отображен", phonesPage.filterForm.isSelectedFilterVisible());
    }

    @Owner("German Suslov")
    @DisplayName("Соответствие количества доступных товаров на странице с категорией \"Мобильные телефоны\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Электроника\" - \"Смартфоны и телефоны\" -" +
            " \"Мобильные телефоны\"  - Страница \"Мобильные телефоны\" открыта, " +
            "количество доступных товаров отображено\n" +
            "3. Нажать кнопку \"Цвет\" - Dropdown с чекбоксами отображен.\n" +
            "4. Выбрать чекбокс \"Бежевый\" - Фильтр \"бежевый\" отображен\n" +
            "5. Сравнить количество товаров до фильтра и после - Количество товаров после фильтра стало меньше")
    @Test
    public void categoryPageGoodsAmountTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.catalogForm.catalogBtnClick();

        mainPage.catalogForm.mainCategoryMove("Электроника");
        mainPage.catalogForm.innerCategoryClick("Смартфоны и телефоны");
        mainPage.catalogForm.innerCategoryClick("Мобильные телефоны");
        CategoryPage phonesPage = new CategoryPage("Мобильные телефоны");
        Assert.assertTrue("Страница категории \"Мобильные телефоны\" не открыта", phonesPage.isOpen());

        Integer amountBeforeFilter = phonesPage.getGoodsAmount();

        phonesPage.filterForm.filterButtonClick("Цвет");
        Assert.assertTrue("Dropdown список не отображен", phonesPage.filterForm.isFilterDropdownOpen());

        phonesPage.filterForm.filterCheckboxClick("бежевый");
        Assert.assertTrue("Выбранный фильтр не отображен", phonesPage.filterForm.isSelectedFilterVisible());

        Integer amountAfterFilter = phonesPage.getGoodsAmount();
        Assert.assertTrue("Количество товара после фильтра больше или равно количеству товаров до фильтра",
                amountAfterFilter < amountBeforeFilter);
    }

    @Owner("German Suslov")
    @DisplayName("Изменение размера карточки товаров на странице с категорией \"Садовая техника\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Бытовая техника\" - \"Садовая техника\"" +
            "  - Страница \"Садовая техника\" открыта\n" +
            "3. Нажать кнопку \"Большой размер карточек\" - Карточки товаров отображены\n" +
            "4. Нажать кнопку \"Маленький размер карточек\" - Размер карточек уменьшен")
    @Test
    public void categoryPageCartSizeTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.catalogForm.catalogBtnClick();

        mainPage.catalogForm.mainCategoryMove("Электроника");
        mainPage.catalogForm.innerCategoryClick("Смартфоны и телефоны");
        mainPage.catalogForm.innerCategoryClick("Мобильные телефоны");
        CategoryPage phonesPage = new CategoryPage("Мобильные телефоны");
        Assert.assertTrue("Страница категории \"Мобильные телефоны\" не открыта", phonesPage.isOpen());

        phonesPage.bigCartsButtonClick();
        Integer bigCartArea = phonesPage.getProductCartArea(1);
        phonesPage.smallCartsButtonClick();
        Integer smallCartArea = phonesPage.getProductCartArea(1);
        Assert.assertTrue("Размер карточек не уменьшился", bigCartArea > smallCartArea);
    }
}
