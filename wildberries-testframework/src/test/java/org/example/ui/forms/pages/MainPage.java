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
    private final ProductCard promoCards = new ProductCard("Карточки промоакций",
            "//li[contains(@class, 'promo__item')]");
    private final Text mainPageText = new Text("Текст внизу главной страницы",
            "//div[contains(@class, 'text_block')]");
    String information;

    public MainPage() {
        super.uniqueElement = uniqueElement;
        this.headerForm = new HeaderForm();
    }

    public void open() {
        Browser.openUrl(DataHelper.getStartUrl());
        getWaiter().waitForElementToBeVisible(uniqueElement);
    }

    public boolean mainPageTextIsDisplayed() {
        Browser.scrollPageToBottom();
        getWaiter().waitForElementToBeVisible(mainPageText);
        return mainPageText.isDisplayed();
    }

    public Integer getPromoCardsAmount() {
        getWaiter().waitForElementToBeVisible(uniqueElement);
        List<SelenideElement> promoCardsList = promoCards.getElements();
        return promoCardsList.size();
    }

    public boolean containsInformation(String keyWord) {
        if (information == null) {
            information = Browser.getText(mainPageText);
        }
        return information.contains(keyWord);
    }
}
