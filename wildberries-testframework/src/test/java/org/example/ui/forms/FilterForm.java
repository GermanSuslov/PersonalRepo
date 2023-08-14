package org.example.ui.forms;

import org.example.ui.elements.*;
import org.example.ui.utils.Waiter;

public class FilterForm extends BaseForm {
    private final Button categoryButton = new Button("Кнопка категорий",
            "//button[contains(@class,'dropdown-filter__btn--burger')]");
    private final Dropdown filterDropdown = new Dropdown("Dropdown список",
            "//div[contains(@class, 'dropdown')]//div[@class = 'filter']");
    private final String filterButtonSample = "//button[contains(@class, 'dropdown-filter__btn')][contains(text(), '%s')]";
    private final String filterCheckboxSample = "//div[contains(@class, 'checkbox-with-text')]//span[contains(text(), '%s')]";
    private final String sortParameterSample = "//span[text() = '%s']";
    private final Button showButton = new Button("Кнопка показать",
            "//button[contains(@class, 'filters-desktop__btn-main')]");
    private final Table additionalFilterPopUp = new Table("Окно с доп фильтрами",
            "//div[contains(@class, 'filters-desktop')][contains(@class, 'shown')]");
    private final Field upperPriceField = new Field("Цена - До",
            "//input[contains(@class, 'j-price')][@name = 'endN']");
    private String additionalFilterCheckboxSample =
            "//div[contains(@class, 'filter')]//span[contains(text(), '%s')]";
    private Button filterButton;
    private Button sortParameterButton;
    private final String selectedFilterButtonSample = "//span[contains(@class, 'your-choice__btn')][contains(text(), '%s')]";
    private Button selectedFilterButton;
    private CheckBox additionalFilterCheckbox;

    public FilterForm() {
        super.uniqueElement = categoryButton;
    }

    public void filterButtonClick(String text) {
        String filterButtonLocator = String.format(filterButtonSample, text);
        filterButton = new Button(text, filterButtonLocator);
        filterButton.click();
        getWaiter().waitForElementToBeVisible(filterDropdown);
    }
    public void additionalFilterCheckboxClick(String filter) {
        String locator = String.format(additionalFilterCheckboxSample, filter);
        additionalFilterCheckbox = new CheckBox(filter, locator);
        additionalFilterCheckbox.click();
    }
    public void allFilterButtonClick() {
        String filterButtonLocator = String.format(filterButtonSample, "Все фильтры");
        filterButton = new Button("Все фильтры", filterButtonLocator);
        filterButton.click();
        getWaiter().waitForElementToBeVisible(additionalFilterPopUp);
    }
    public void enterUpperPrice(Integer price) {
        getWaiter().waitForElementToBeVisible(upperPriceField);
        upperPriceField.clearField();
        getWaiter().waitForFieldToBeEmpty(upperPriceField);
        upperPriceField.enterText(price.toString());
    }
    public void showButtonClick() {
        getWaiter().waitForElementToBeVisible(showButton);
        showButton.click();
        getWaiter().waitForElementToBeInvisible(additionalFilterPopUp);
    }
    public void filterCheckboxClick(String text) {
        new CheckBox(text, String.format(filterCheckboxSample, text)).click();
        selectedFilterButton = new Button(text, String.format(selectedFilterButtonSample, text));
    }

    public boolean isAdditionalFilterPopUpOpen() {
        return additionalFilterPopUp.isDisplayed();
    }

    public void sortParameterButtonClick(String text) {
        sortParameterButton = new Button(text, String.format(sortParameterSample, text));
        sortParameterButton.click();
        getWaiter().waitByMillis(1000);
    }
    public boolean isSelectedFilterVisible() {
        getWaiter().waitForElementToBeVisible(selectedFilterButton);
        return selectedFilterButton.isDisplayed();
    }

    public boolean isFilterDropdownOpen() {
        return filterDropdown.isDisplayed();
    }
}
