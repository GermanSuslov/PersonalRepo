package by.a1qa.ui.forms.pages;

import by.a1qa.ui.elements.Button;
import by.a1qa.ui.elements.CheckBox;
import by.a1qa.ui.elements.Table;
import by.a1qa.ui.elements.TextBox;
import by.a1qa.ui.forms.SteamForm;
import org.openqa.selenium.By;

public class NewsPage extends SteamForm {
    private final Button optionsAndFiltersButton = new Button("Options and filter button",
            By.xpath("//div[contains(@class, 'OpenFilterSettings')]"));
    private final TextBox resultMessage = new TextBox("Search result message",
            By.xpath("//div[contains(@class, 'eventcalendar_EndOfRows')]//div"));
    private final CheckBox selectedCheckBoxList = new CheckBox("All selected checkboxes in " +
            "'SHOW THESE TYPES OF POSTS' block",
            By.xpath("//div[contains(text(), 'Show these types')]/following-sibling::" +
                    "div[contains(@class, 'Focusable')]//div[contains(@class, 'DialogCheckbox') and"
                    + " (contains(@class, 'Active'))]"));

    public NewsPage() {
        super(new Table("Steam news sidebar",
                By.xpath("//div[contains(@class, 'EventCalendarContainer')]")));
    }

    public void optionsAndFiltersButtonClick() {
        waiter.waitForElementToBeClickable(optionsAndFiltersButton);
        optionsAndFiltersButton.click();
    }

    public void unselectAllCheckBoxFilters() {
        waiter.waitForElementsToBeVisible(selectedCheckBoxList);
        selectedCheckBoxList.clickAll();
    }

    public String getResultMessage() {
        waiter.waitForElementToBeVisible(resultMessage);
        return resultMessage.getText();
    }
}
