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
    private final Table uniqueElement = new Table("Рекламная карусель",
           "//div[contains(@data-block-type, 'main')]");

    public MainPage() {
        super.uniqueElement = uniqueElement;
        this.catalogForm = new CatalogForm();
    }

    public void open() {
        Browser.openUrl(DataHelper.getStartUrl());
        Waiter.getWaiter().waitForElementToBeVisible(uniqueElement);
    }
}
