package org.example.ui.forms.pages;

import org.example.ui.elements.Button;
import org.example.ui.elements.Number;
import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.example.ui.forms.FilterForm;
import org.example.ui.utils.Waiter;

public class CategoryPage extends BaseForm {
    public CatalogForm catalogForm;
    public FilterForm filterForm;
    private Number goodsAmount = new Number("Количество товара",
            "//span[contains(@data-link, 'html')]");
    private Button smallCartsButton = new Button("Кнопка маленькие карточки товаров",
            "//a[contains(@class, 'card-sizes__btn--c516x688')]");
    private Button bigCartsButton = new Button("Кнопка большие карточки товаров",
            "//a[contains(@class, 'card-sizes__btn--big')]");
    private Table productCart;

    public CategoryPage(String title) {
        this.catalogForm = new CatalogForm();
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
        productCart = new Table("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        return productCart.getTableArea();
    }



}
