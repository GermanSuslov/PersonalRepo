package org.example.ui.tests.pages_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import org.example.ui.forms.pages.CategoryPage;
import org.example.ui.forms.pages.MainPage;
import org.example.ui.forms.pages.ProductCardPage;
import org.example.ui.tests.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Feature("Тесты категорий товаров")
public class CategoryPageTest extends BaseTest {
    private MainPage mainPage;

    @Before
    public void openMainPage() {
        mainPage = new MainPage();
        mainPage.open();
    }

    @Owner("German Suslov")
    @DisplayName("Выбор фильтра \"Бренд - Nokia\" на странице \"Мобильные телефоны\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Электроника\" - \"Смартфоны и телефоны\" -" +
            " \"Мобильные телефоны\"  - Страница \"Мобильные телефоны\" открыта\n" +
            "3. Нажать кнопку \"Бренды\" - Dropdown с чекбоксами отображен.\n" +
            "4. Выбрать чекбокс \"Nokia\" - Фильтр \"Nokia\" отображен")
    @Test
    public void categoryPageFilterTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Электроника");
        mainPage.headerForm.catalogForm.innerCategoryClick("Смартфоны и телефоны");
        mainPage.headerForm.catalogForm.innerCategoryClick("Мобильные телефоны");
        CategoryPage categoryPage = new CategoryPage("Мобильные телефоны");
        Assert.assertTrue("Страница категории \"Мобильные телефоны\" не открыта", categoryPage.isOpen());

        categoryPage.filterForm.filterButtonClick("Бренд");
        Assert.assertTrue("Dropdown список не отображен", categoryPage.filterForm.isFilterDropdownOpen());

        categoryPage.filterForm.filterCheckboxClick("Nokia");
        Assert.assertTrue("Выбранный фильтр не отображен", categoryPage.filterForm.isSelectedFilterVisible());
    }

    @Owner("German Suslov")
    @DisplayName("Соответствие количества доступных товаров на странице с категорией \"Столы\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Мебель\" - \"Мебель для гостиной\" -" +
            " \"Столы\"  - Страница \"Столы\" открыта, " +
            "количество доступных товаров отображено\n" +
            "3. Нажать кнопку \"Цвет\" - Dropdown с чекбоксами отображен.\n" +
            "4. Выбрать чекбокс \"Бежевый\" - Фильтр \"бежевый\" отображен\n" +
            "5. Сравнить количество товаров до фильтра и после - Количество товаров после фильтра стало меньше")
    @Test
    public void categoryPageGoodsAmountTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Мебель");
        mainPage.headerForm.catalogForm.innerCategoryClick("Мебель для гостиной");
        mainPage.headerForm.catalogForm.innerCategoryClick("Столы");
        CategoryPage categoryPage = new CategoryPage("Столы");
        Assert.assertTrue("Страница категории \"Столы\" не открыта", categoryPage.isOpen());

        Integer amountBeforeFilter = categoryPage.getGoodsAmount();

        categoryPage.filterForm.filterButtonClick("Цвет");
        Assert.assertTrue("Dropdown список не отображен", categoryPage.filterForm.isFilterDropdownOpen());

        categoryPage.filterForm.filterCheckboxClick("бежевый");
        Assert.assertTrue("Выбранный фильтр не отображен", categoryPage.filterForm.isSelectedFilterVisible());

        Integer amountAfterFilter = categoryPage.getGoodsAmount();
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
        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Бытовая техника");
        mainPage.headerForm.catalogForm.innerCategoryClick("Садовая техника");
        CategoryPage categoryPage = new CategoryPage("Садовая техника");
        Assert.assertTrue("Страница категории \"Садовая техника\" не открыта", categoryPage.isOpen());

        categoryPage.bigCartsButtonClick();
        Integer bigCartArea = categoryPage.getProductCartArea(1);
        categoryPage.smallCartsButtonClick();
        Integer smallCartArea = categoryPage.getProductCartArea(1);
        Assert.assertTrue("Размер карточек не уменьшился", bigCartArea > smallCartArea);
    }

    @Owner("German Suslov")
    @DisplayName("Сортировка товаров на странице категории")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Дом\" - \"Коврики\"" +
            "  - Страница \"Коврики\" открыта\n" +
            "3. Выбрать сортировку \"По возрастанию цены\" - Карточки товаров изменились")
    @Test
    public void categoryPageSortTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Дом");
        mainPage.headerForm.catalogForm.innerCategoryClick("Коврики");
        CategoryPage categoryPage = new CategoryPage("Коврики");
        Assert.assertTrue("Страница категории \"Коврики\" не открыта", categoryPage.isOpen());

        long firstCartIdBefore = categoryPage.getProductCartId(1);
        categoryPage.filterForm.filterButtonClick("По популярности");
        categoryPage.filterForm.sortParameterButtonClick("По возрастанию цены");
        long firstCartIdAfter = categoryPage.getProductCartId(1);
        Assert.assertNotEquals("Карточки товаров не изменились", firstCartIdBefore, firstCartIdAfter);
    }

    @Owner("German Suslov")
    @DisplayName("Применение фильтра \"Объем оперативной памяти (Гб) - 8 ГБ\" на странице категории \"Ноутбуки\"")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Электроника\" - \"Ноутбуки и компьютеры\"" +
            " - \"Ноутбуки\" - Страница \"Ноутбуки и ультрабуки\" открыта\n" +
            "3. Нажать \"Все фильтры\" - Поп-ап окно с фильтрами отображено\n" +
            "4. Выбрать фильтр \"Объем оперативной памяти (Гб) - 8 ГБ\", нажать кнопку показать - " +
            "Поп-ап окно с фильтрами закрылось, количество товаров уменьшилось")
    @Test
    public void categoryPageAdditionalFilterTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());
        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Электроника");
        mainPage.headerForm.catalogForm.innerCategoryClick("Ноутбуки и компьютеры");
        mainPage.headerForm.catalogForm.innerCategoryClick("Ноутбуки");
        CategoryPage categoryPage = new CategoryPage("Ноутбуки и ультрабуки");
        Assert.assertTrue("Страница категории \"Ноутбуки и ультрабуки\" не открыта", categoryPage.isOpen());

        Integer productAmount = categoryPage.getProductAmount();
        categoryPage.filterForm.allFilterButtonClick();
        Assert.assertTrue("Поп-ап окно с фильтрами не отображено",
                categoryPage.filterForm.isAdditionalFilterPopUpOpen());

        categoryPage.filterForm.additionalFilterCheckboxClick("8 ГБ");
        categoryPage.filterForm.showButtonClick();
        Assert.assertFalse("Поп-ап окно с фильтрами не закрылось",
                categoryPage.filterForm.isAdditionalFilterPopUpOpen());
        Integer productAmountAfterFilter = categoryPage.getProductAmount();
        Assert.assertTrue("Количество товаров не уменьшилось",
                productAmountAfterFilter < productAmount);
    }

    @Owner("German Suslov")
    @DisplayName("Фильтр товаров в категории \"Конструкторы\" по цене до 1000 рублей")
    @Description("1. Зайти на главную страницу wildberries - Главная страница открыта\n" +
            "2. Нажать на кнопку \"Каталог\", выбрать категорию \"Детям\" - \"Конструкторы\"" +
            "  - Страница \"Конструкторы\" открыта\n" +
            "3. Выбрать \"Все фильтры\" - Окно с дополнительными фильтрами открыто\n" +
            "4. Установить 1000 в поле \"Цена - До\" нажать на кнопку показать - " +
            "Окно с доп фильтрами закрылось\n" +
            "5. Открыть первую карточку товара - Цена меньше 1000 рублей\n")
    @Test
    public void productCartPriceTest() {
        Assert.assertTrue("Главная страница не открыта", mainPage.isOpen());

        mainPage.headerForm.catalogForm.catalogBtnClick();

        mainPage.headerForm.catalogForm.mainCategoryMove("Детям");
        mainPage.headerForm.catalogForm.innerCategoryClick("Конструкторы");
        CategoryPage categoryPage = new CategoryPage("Конструкторы");
        Assert.assertTrue("Страница категории \"Конструкторы\" не открыта", categoryPage.isOpen());

        categoryPage.filterForm.allFilterButtonClick();
        Assert.assertTrue("Окно с дополнительными фильтрами не открыто",
                categoryPage.filterForm.isAdditionalFilterPopUpOpen());

        categoryPage.filterForm.enterUpperPrice(1000);
        categoryPage.filterForm.showButtonClick();
        Assert.assertFalse("Окно с дополнительными фильтрами открыто",
                categoryPage.filterForm.isAdditionalFilterPopUpOpen());

        categoryPage.productCartClick(1);
        ProductCardPage productCardPage = new ProductCardPage();
        Integer price = productCardPage.getPrice();
        Assert.assertTrue("Цена товара больше 1000 рублей", price < 1000);
    }
}
