package org.example.ui.forms;

import org.example.ui.elements.Field;
import org.example.ui.utils.Browser;
import org.example.ui.utils.Waiter;

public class SearchForm extends BaseForm {
    private final Field searchField = new Field("Поле строки поиска",
            "//div[contains(@class, 'search-catalog__block')]");

    public SearchForm() {
        super.uniqueElement = searchField;
    }

    public void search(String text) {
        Waiter.getWaiter().waitForElementToBeVisible(searchField);
        Browser.enterText(searchField.getSelenideElement(), text);
        Browser.pressEnterButton();
    }
}
