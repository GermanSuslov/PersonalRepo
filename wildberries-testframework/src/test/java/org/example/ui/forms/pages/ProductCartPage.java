package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.elements.Button;
import org.example.ui.elements.Price;
import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.utils.Browser;
import org.example.ui.utils.Waiter;

public class ProductCartPage extends BaseForm {
    private final Price productPrice = new Price("Цена товара",
            "(//ins[contains(@class, 'price-block__final-price')])[1]");
    private final Table productInformation = new Table("О товаре",
            "//section[contains(@class, 'product-page__details-section')]");
    private final Title seeMore = new Title("Смотрите также",
            "//h2[contains(@class, 'goods-slider__header')]");
    private final Button unrollButtons = new Button("Кнопки развернуть",
            "//button[contains(@class, 'collapsible__toggle')]");
    private String information;

    public ProductCartPage() {
        super.uniqueElement = new Title("Страница карточки товара",
                "//div[contains(@class, 'product-page__grid')]");
    }

    public boolean containsInformation(String keyWord) {
        if (information == null) {
            unrollAllText();
            information = productInformation.getSelenideElement().getText();
        }
        return information.contains(keyWord);
    }

    public void unrollAllText() {
        Browser.scrollPageToBottom();
        Waiter.getWaiter().waitForElementToBeVisible(seeMore);
        unrollButtons.getElements().forEach(SelenideElement::click);
    }

    public Integer getPrice() {
        return productPrice.getPrice();
    }
}
