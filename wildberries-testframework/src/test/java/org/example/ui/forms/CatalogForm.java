package org.example.ui.forms;

import org.checkerframework.checker.units.qual.C;
import org.example.ui.elements.Button;
import org.example.ui.elements.Category;
import org.example.ui.elements.Table;
import org.example.ui.utils.Waiter;
import org.openqa.selenium.By;

public class CatalogForm extends BaseForm {
    private final Button catalog = new Button("Кнопка каталога",
            "//button[contains(@aria-label, 'Навигация по сайту')]");
    private final Button closeCatalog = new Button("Кнопка закрытия каталога",
            "//button[contains(@class, 'menu-burger__close')]");
    private Category mainListCategory = new Category("Главная категория",
            "//a[contains(@class, 'main-list-link')][contains(text(), '%s')]");
    private Category innerListCategory = new Category("Внутренняя категория",
            "//div[contains(@class, 'list-item--active')]//*[contains(@class, 'menu-drop')][contains(text(), '%s')]");
    private final Table catalogMenu = new Table("Таблица каталога",
            "//div[contains(@class, 'menu-burger__main')]");
    private final Table innerMenu = new Table("Внутренне меню каталога",
            "//div[contains(@class, 'menu-burger__drop-list-item--active')]");

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

    public boolean catalogMenuIsHidden() {
        Waiter.getWaiter().waitForElementToBeInvisible(catalogMenu);
        return catalogMenu.isHidden();
    }
    public void innerCategoryClick(String category) {
        innerListCategory.setCategory(category);
        Waiter.getWaiter().waitForElementToBeClickable(innerListCategory.getCategoryElement());
        innerListCategory.click();
    }

    public void innerCategoryMove(String category) {
        innerListCategory.setCategory(category);
        Waiter.getWaiter().waitForElementToBeClickable(innerListCategory.getCategoryElement());
        innerListCategory.moveToElement(innerListCategory.getCategoryElement());
    }

    public void mainCategoryMove(String category) {
        mainListCategory.setCategory(category);
        Waiter.getWaiter().waitForElementToBeClickable(mainListCategory.getCategoryElement());
        mainListCategory.moveToElement(mainListCategory.getCategoryElement());
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
