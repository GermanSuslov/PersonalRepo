package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.ui.elements.Button;
import org.example.ui.elements.Price;
import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.HeaderForm;
import org.example.ui.utils.Browser;

public class ProductCardPage extends BaseForm {
    public HeaderForm headerForm;
    private final Price productPrice = new Price("Цена товара",
            "(//ins[contains(@class, 'price-block__final-price')])[1]");
    private final Table productInformation = new Table("О товаре",
            "//section[contains(@class, 'product-page__details-section')]");
    private final Title seeMore = new Title("Смотрите также",
            "//h2[contains(@class, 'goods-slider__header')]");
    private final Button addToBasketButton = new Button("Кнопка добавить в корзину",
            "(//div[@class = 'order']//button[@class = 'btn-main'])[2]");
    private final Button unrollButtons = new Button("Кнопки развернуть",
            "//button[contains(@class, 'collapsible__toggle')]");
    private final Table basketPopUp = new Table("Поп-ап окно - товар в корзине",
            "//p[contains(text(), 'Товар добавлен в корзину')]");
    private final Button toBasketButton = new Button("Перейтив корзину",
            "(//div[@class = 'order']//a[contains(@class, 'btn-base')])[2]");
    private final Button sizeTableButton = new Button("Таблица размеров",
            "//button[contains(@class, 'sizes-table__btn')]");
    private final Table sizeTablePopUp = new Table("Поп-ап с таблицей размеров",
            "//div[contains(@class, 'popup-table-of-sizes')]");
    private final Button sellerButton = new Button("Кнопка с именем продавца",
            "//a[contains(@class, 'seller-info__name')]");
    private String information;

    public ProductCardPage() {
        this.headerForm = new HeaderForm();
        super.uniqueElement = new Title("Страница карточки товара",
                "//div[contains(@class, 'product-page__grid')]");
    }

    public boolean sellerButtonIsDisplayed() {
        getWaiter().waitForElementToBeVisible(sellerButton);
        return sellerButton.isDisplayed();
    }

    public void sellerButtonClick() {
        getWaiter().waitForElementToBeVisible(sellerButton);
        sellerButton.click();
    }

    public boolean containsInformation(String keyWord) {
        if (information == null) {
            unrollAllText();
            information = Browser.getText(productInformation);
        }
        return information.contains(keyWord);
    }

    public void sizeTableButtonClick() {
        getWaiter().waitForElementToBeVisible(sizeTableButton);
        sizeTableButton.click();
    }

    public boolean sizeTablePopUpIsDisplayed() {
        getWaiter().waitForElementToBeVisible(sizeTablePopUp);
        return sizeTablePopUp.isDisplayed();
    }

    public boolean basketPopUpIsDisplayed() {
        getWaiter().waitForElementToBeVisible(basketPopUp);
        return basketPopUp.isDisplayed();
    }

    public String getBuyButtonText() {
        return Browser.getText(toBasketButton);
    }

    @Step("Кликнуть по кнопке 'Добавить в корзину'")
    public void clickAddToBasketButton() {
        getWaiter().waitForElementToBeVisible(addToBasketButton);
        addToBasketButton.click();
    }

    public void unrollAllText() {
        Browser.scrollPageToBottom();
        getWaiter().waitForElementToBeVisible(seeMore);
        unrollButtons.getElements().forEach(SelenideElement::click);
    }

    public Integer getPrice() {
        return productPrice.getPrice();
    }
}
