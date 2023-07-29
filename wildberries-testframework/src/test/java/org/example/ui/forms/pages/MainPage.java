package org.example.ui.forms.pages;

import org.example.ui.elements.Button;
import org.example.ui.elements.Price;
import org.example.ui.elements.Table;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;
import org.openqa.selenium.By;

public class MainPage extends BaseForm {
    public CatalogForm catalogForm;
    private final Button clothes = new Button("Clothes button",
            "//a[contains(text(), 'Одежда и обувь')]");

    private final Table uniqueElement = new Table("Рекламная карусель",
           "//div[contains(@data-block-type, 'main')]");

    private final Price firstBestDealsPrice = new Price("Первая цена товара из лучших предложений",
            "(//div[@class = 'ba3y']//div[@class = 'eh6'])[1]//span[contains(@class, 'c3')]");

    public MainPage() {
        super.uniqueElement = uniqueElement;
        this.catalogForm = new CatalogForm();
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
