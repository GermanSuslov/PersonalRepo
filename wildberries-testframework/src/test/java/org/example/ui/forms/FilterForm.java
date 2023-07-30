package org.example.ui.forms;

import org.example.ui.elements.Button;
import org.example.ui.elements.CheckBox;
import org.example.ui.elements.Dropdown;
import org.example.ui.utils.Waiter;

public class FilterForm extends BaseForm {
    private final Button categoryButton = new Button("Кнопка категорий",
            "//button[contains(@class,'dropdown-filter__btn--burger')]");
    private final String filterButtonSample = "//button[contains(@class, 'dropdown-filter__btn')][contains(text(), '%s')]";
    private final Dropdown filterDropdown = new Dropdown("Dropdown список",
            "//div[contains(@class, 'dropdown')]//div[@class = 'filter']");
    private final String filterCheckboxSample = "//div[contains(@class, 'checkbox-with-text')]//span[contains(text(), '%s')]";
    private final String selectedFilterButtonSample = "//span[contains(@class, 'your-choice__btn')][contains(text(), '%s')]";
    private Button selectedFilterButton;

    public FilterForm() {
        super.uniqueElement = categoryButton;
    }

    public void filterButtonClick(String text) {
        String filterButtonLocator = String.format(filterButtonSample, text);
        new Button(text, filterButtonLocator).click();
        Waiter.getWaiter().waitForElementToBeVisible(filterDropdown);
    }
    public void filterCheckboxClick(String text) {
        new CheckBox(text, String.format(filterCheckboxSample, text)).click();
        selectedFilterButton = new Button(text, String.format(selectedFilterButtonSample, text));
    }
    public boolean isSelectedFilterVisible() {
        Waiter.getWaiter().waitForElementToBeVisible(selectedFilterButton);
        return selectedFilterButton.isDisplayed();
    }

    public boolean isFilterDropdownOpen() {
        return filterDropdown.isDisplayed();
    }
}
