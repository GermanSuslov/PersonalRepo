package org.example.ui.forms.pages;

import org.example.ui.elements.Price;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;

public class ProductCartPage extends BaseForm {
    private final Price productPrice = new Price("Цена товара",
            "(//ins[contains(@class, 'price-block__final-price')])[1]");
    public ProductCartPage(String title) {
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }

    public Integer getPrice() {
        return productPrice.getPrice();
    }
}
