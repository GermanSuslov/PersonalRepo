package org.example.ui.forms.pages;

import org.example.ui.elements.Button;
import org.example.ui.elements.Table;
import org.example.ui.forms.BaseForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.openqa.selenium.By;

public class MainPage extends BaseForm {
    private final Button catalog = new Button("Catalog button",
            By.xpath("//button[@type='button']//span[contains(text(), 'Каталог')]"));
    private final Table catalogMenu = new Table("Catalog table",
            By.xpath("//div[contains(@class, 'u7c')]"));

    public void open() {
        Browser.openUrl(DataHelper.getStartUrl());
    }

    public boolean catalogMenuIsVisible() {
        return catalogMenu.isDisplayed();
    }

    public void catalogBtnClick() {
        getWaiter().waitForElementToBeClickable(catalog);
        catalog.click();
    }
}
