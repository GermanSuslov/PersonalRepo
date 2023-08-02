package org.example.ui.forms;

import org.example.ui.elements.Button;
import org.example.ui.utils.Waiter;

public class HeaderForm extends BaseForm {
    public CatalogForm catalogForm;
    public SearchForm searchForm;
    public Button mainPageButton = new Button("Кнопка главной страницы",
            "//a[@data-wba-header-name = 'Main']");
    public Button basketButton = new Button("Кнопка корзины",
            "//a[@data-wba-header-name = 'Cart']");

    public HeaderForm() {
        super.uniqueElement = mainPageButton;
        this.catalogForm = new CatalogForm();
        this.searchForm = new SearchForm();
    }

    public void basketButtonClick() {
        Waiter.getWaiter().waitForElementToBeVisible(basketButton);
        basketButton.click();
    }
}
