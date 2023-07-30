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
    @DisplayName("Переход на страницу \"Мобильные телефоны\" с помощью каталога")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\" - Поп-ап окно с каталогом отображено\n" +
            "3. Выбрать категорию \"Электроника\" - \"Смартфоны и телефоны\" - \"Мобильные телефоны\"  - Страница \"Мобильные телефоны\" открыта")
    @Test
    public void categoryPageTest() {
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
}
