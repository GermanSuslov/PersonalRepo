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
    private Number productAmount = new Number("Количество товаров",
            "//span[contains(@class, 'goods-count')]//span[contains(@data-link, 'html')]");
    private ProductCard productCard;

    public CategoryPage(String title) {
        this.headerForm = new HeaderForm();
        this.filterForm = new FilterForm();
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }

    public Integer getProductAmount() {
        getWaiter().waitForElementToBeVisible(productAmount);
        return productAmount.getNumber();
    }

    public Integer getGoodsAmount() {
        getWaiter().waitForElementToBeVisible(goodsAmount);
        return goodsAmount.getNumber();
    }

    public void bigCartsButtonClick() {
        getWaiter().waitForElementToBeClickable(bigCartsButton);
        bigCartsButton.click();
    }
    public void smallCartsButtonClick() {
        getWaiter().waitForElementToBeClickable(smallCartsButton);
        smallCartsButton.click();
    }

    public Integer getProductCartArea(Integer cartNumber) {
        productCard = new ProductCard("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        getWaiter().waitForElementToBeVisible(productCard);
        return productCard.getBorderArea();
    }

    public Integer getProductCartId(Integer cartNumber) {
        productCard = new ProductCard("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        getWaiter().waitForElementToBeVisible(productCard);
        return productCard.getId();
    }

    public void productCartClick(Integer cartNumber) {
        productCard = new ProductCard("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        getWaiter().waitForElementToBeVisible(productCard);
        productCard.click();
    }
}
