package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.elements.*;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.HeaderForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;

import java.util.List;

public class MainPage extends BaseForm {
    public HeaderForm headerForm;
    private final Table uniqueElement = new Table("Рекламная карусель",
           "//div[contains(@data-block-type, 'main')]");
    private final ProductCart promoCards = new ProductCart("Карточки промоакций",
            "//li[contains(@class, 'promo__item')]");

    public MainPage() {
        super.uniqueElement = uniqueElement;
        this.headerForm = new HeaderForm();
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
