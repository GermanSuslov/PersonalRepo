package org.example.ui.forms.pages;

import org.example.ui.elements.ProductCard;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.utils.Waiter;

public class SearchResultPage extends BaseForm {
    private ProductCard productCard;
    public SearchResultPage(String title) {
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }

    public void productCartClick(Integer cartNumber) {
        productCard = new ProductCard("Карточка товара №" + cartNumber,
                String.format("(//article[contains(@class, 'product-card')])[%d]", cartNumber));
        getWaiter().waitForElementToBeVisible(productCard);
        productCard.click();
    }
}
