package org.example.ui.forms.pages;

import org.example.ui.elements.Button;
import org.example.ui.elements.Price;
import org.example.ui.elements.Table;
import org.example.ui.forms.BaseForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;
import org.openqa.selenium.By;

public class MainPage extends BaseForm {
    private final Button catalog = new Button("Catalog button",
            By.xpath("//button[contains(@aria-label, 'Навигация по сайту')]"));
    private final Button closeCatalog = new Button("Close catalog button",
            By.xpath("//button[contains(@class, 'menu-burger__close')]"));
    private final Button clothes = new Button("Clothes button",
            By.xpath("//a[contains(text(), 'Одежда и обувь')]"));
    private final Table catalogMenu = new Table("Catalog table",
            By.xpath("//div[contains(@class, 'menu-burger__main')]"));
    private final Table uniqueElement = new Table("Рекламная карусель",
            By.xpath("//div[contains(@data-block-type, 'main')]"));
    private final Price firstBestDealsPrice = new Price("Первая цена товара из лучших предложений",
            By.xpath("(//div[@class = 'ba3y']//div[@class = 'eh6'])[1]//span[contains(@class, 'c3')]"));

    public MainPage() {
        super.uniqueElement = uniqueElement;
    }

    public boolean catalogMenuIsVisible() {
        Waiter.getWaiter().waitForElementToBeVisible(catalogMenu);
        return catalogMenu.isDisplayed();
    }

    public boolean catalogMenuIsHidden() {
        Waiter.getWaiter().waitForElementToBeInvisible(catalogMenu);
        return catalogMenu.isHidden();
    }

    public void catalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(catalog);
        catalog.click();
    }

    public void closeCatalogBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(closeCatalog);
        closeCatalog.click();
    }

    public void clothesBtnClick() {
        Waiter.getWaiter().waitForElementToBeClickable(clothes);
        clothes.click();
    }

    public boolean firstBestDealsPriceIsVisible() {
        Waiter.getWaiter().waitForElementsToBeVisible(firstBestDealsPrice.getElements());
        return firstBestDealsPrice.areDisplayed();
    }

    public void open() {
        Browser.openUrl(DataHelper.getStartUrl());
    }
}
