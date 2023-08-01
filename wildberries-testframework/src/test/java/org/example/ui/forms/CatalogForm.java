package org.example.ui.forms;

import org.example.ui.elements.Button;
import org.example.ui.elements.Category;
import org.example.ui.elements.Table;
import org.example.ui.utils.Waiter;

public class CatalogForm extends BaseForm {
    private final Button catalog = new Button("Кнопка каталога",
            "//button[contains(@aria-label, 'Навигация по сайту')]");
    private final Button closeCatalog = new Button("Кнопка закрытия каталога",
            "//button[contains(@class, 'menu-burger__close')]");
    private Category mainListCategory;
    private final String mainListCategorySample = "//a[contains(@class, 'main-list-link')][contains(text(), '%s')]";
    private Category innerListCategory;
    private final String innerListCategorySample = "//div[contains(@class, 'list-item--active')]" +
            "//*[contains(@class, 'menu-drop')][text() = '%s']";
    private final Table catalogMenu = new Table("Таблица каталога",
            "//div[contains(@class, 'menu-burger__main')]");
    private final Table innerMenu = new Table("Внутренне меню каталога",
            "//div[contains(@class, 'menu-burger__drop-list-item--active')]");
    private final Button mainPageButton = new Button("Кнопка главной страницы",
            "//a[@data-wba-header-name = 'Main']");

    public CatalogForm() {
        super.uniqueElement = closeCatalog;
    }

    public boolean catalogMenuIsVisible() {
        Waiter.getWaiter().waitForElementToBeVisible(catalogMenu);
        return catalogMenu.isDisplayed();
    }

    public boolean catalogInnerMenuIsVisible() {
        Waiter.getWaiter().waitForElementToBeVisible(innerMenu);
        return innerMenu.isDisplayed();
    }

    public void mainPageButtonClick() {
        Waiter.getWaiter().waitForElementToBeClickable(mainPageButton);
        mainPageButton.click();
    }

    public boolean catalogMenuIsHidden() {
        Waiter.getWaiter().waitForElementToBeInvisible(catalogMenu);
        return catalogMenu.isHidden();
    }

    public void innerCategoryClick(String category) {
        innerListCategory = new Category(category, String.format(innerListCategorySample, category));
        Waiter.getWaiter().waitForElementToBeClickable(innerListCategory);
        innerListCategory.click();
    }

    public void mainCategoryMove(String category) {
        mainListCategory = new Category(category, String.format(mainListCategorySample, category));
        Waiter.getWaiter().waitForElementToBeClickable(mainListCategory);
        mainListCategory.moveToElement();
        Waiter.getWaiter().waitForElementToBeVisible(innerMenu);
    }

    public void catalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(catalog);
        catalog.click();
        Waiter.getWaiter().waitForElementToBeVisible(catalogMenu);
    }

    public void closeCatalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(closeCatalog);
        closeCatalog.click();
    }
}
