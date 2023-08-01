package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.elements.*;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.example.ui.forms.SearchForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;
import org.openqa.selenium.By;

import java.util.List;

public class MainPage extends BaseForm {
    public CatalogForm catalogForm;
    public SearchForm searchForm;
    private final Table uniqueElement = new Table("Рекламная карусель",
           "//div[contains(@data-block-type, 'main')]");
    private final Cart promoCards = new Cart("Карточки промоакций",
            "//li[contains(@class, 'promo__item')]");

    public MainPage() {
        super.uniqueElement = uniqueElement;
        this.catalogForm = new CatalogForm();
        this.searchForm = new SearchForm();
    }

    public void open() {
        Browser.openUrl(DataHelper.getStartUrl());
        Waiter.getWaiter().waitForElementToBeVisible(uniqueElement);
    }

    public Integer getPromoCardsAmount() {
        Waiter.getWaiter().waitForElementToBeVisible(uniqueElement);
        List<SelenideElement> promoCardsList = promoCards.getElements();
        return promoCardsList.size();
    }
}
