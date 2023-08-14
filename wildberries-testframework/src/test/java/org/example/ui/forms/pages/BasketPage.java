package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.elements.ProductCard;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.HeaderForm;
import org.example.ui.utils.Waiter;

import java.util.List;

public class BasketPage extends BaseForm {
    public HeaderForm headerForm;
    private Title basketTitle = new Title("Заголовок корзины",
            "//div[contains(@class, 'basket-section__header-tabs')]");
    private ProductCard cartsInBasket = new ProductCard("Товары в корзине",
            "//span[contains(@class, 'good-info__good-name')]");

    public BasketPage() {
        this.headerForm = new HeaderForm();
        super.uniqueElement = basketTitle;
    }

    public boolean basketContainsProduct(String productName) {
        List<SelenideElement> cartsList = cartsInBasket.getElements();
        getWaiter().waitForElementsToBeVisible(cartsList);
        return cartsList.stream()
                .anyMatch(product -> product.getText().contains(productName));
    }
}
