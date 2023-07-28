package org.example.ui.forms;

import org.example.ui.elements.Button;
import org.example.ui.elements.Category;
import org.example.ui.elements.Table;
import org.example.ui.utils.Waiter;
import org.openqa.selenium.By;

public class CatalogForm extends BaseForm {
    private final Button catalog = new Button("Кнопка каталога",
            By.xpath("//button[contains(@aria-label, 'Навигация по сайту')]"));
    private final Button closeCatalog = new Button("Кнопка закрытия каталога",
            By.xpath("//button[contains(@class, 'menu-burger__close')]"));
    private Category mainListCategory = new Category("Главная категория",
            By.xpath("//a[contains(@class, 'main-list-link')][contains(text(), '%s')]"));
    private final Table catalogMenu = new Table("Таблица каталога",
            By.xpath("//div[contains(@class, 'menu-burger__main')]"));
    public boolean catalogMenuIsVisible() {
        Waiter.getWaiter().waitForElementToBeVisible(catalogMenu);
        return catalogMenu.isDisplayed();
    }
    public boolean catalogMenuIsHidden() {
        Waiter.getWaiter().waitForElementToBeInvisible(catalogMenu);
        return catalogMenu.isHidden();
    }
    public void mainCategoryClick(String category) {
        mainListCategory.setCategory(category);
        Waiter.getWaiter().waitForElementToBeClickable(mainListCategory);
        mainListCategory.click();
    }

    public void mainCategoryMove(String category) {
        mainListCategory.setCategory(category);
        Waiter.getWaiter().waitForElementToBeClickable(mainListCategory);
        mainListCategory.click();
    }

    public void catalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(catalog);
        catalog.click();
    }

    public void closeCatalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(closeCatalog);
        closeCatalog.click();
    }
}
