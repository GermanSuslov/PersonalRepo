package org.example.ui.forms.pages;

import org.example.ui.elements.*;
import org.example.ui.elements.Number;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.FilterForm;
import org.example.ui.forms.HeaderForm;
import org.example.ui.utils.Waiter;

public class CategoryPage extends BaseForm {
    public HeaderForm headerForm;
    public FilterForm filterForm;
    private Number goodsAmount = new Number("Количество товара",
            "//span[contains(@data-link, 'html')]");
    private Button smallCartsButton = new Button("Кнопка маленькие карточки товаров",
            "//a[contains(@class, 'card-sizes__btn--c516x688')]");
    private Button bigCartsButton = new Button("Кнопка большие карточки товаров",
            "//a[contains(@class, 'card-sizes__btn--big')]");
    private ProductCart productCart;

    public CategoryPage(String title) {
        this.headerForm = new HeaderForm();
        this.filterForm = new FilterForm();
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }

    public Integer getGoodsAmount() {
        Waiter.getWaiter().waitForElementToBeVisible(goodsAmount);
        return goodsAmount.getNumber();
    }

    public void bigCartsButtonClick() {
        Waiter.getWaiter().waitForElementToBeClickable(bigCartsButton);
        bigCartsButton.click();
    }
    public void smallCartsButtonClick() {
        Waiter.getWaiter().waitForElementToBeClickable(smallCartsButton);
        smallCartsButton.click();
    }

    public Integer getProductCartArea(Integer cartNumber) {
        productCart = new ProductCart("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        Waiter.getWaiter().waitForElementToBeVisible(productCart);
        return productCart.getBorderArea();
    }

    public Integer getProductCartId(Integer cartNumber) {
        productCart = new ProductCart("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        Waiter.getWaiter().waitForElementToBeVisible(productCart);
        return productCart.getId();
    }

    public void productCartClick(Integer cartNumber) {
        productCart = new ProductCart("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        Waiter.getWaiter().waitForElementToBeVisible(productCart);
        productCart.click();
    }
}
