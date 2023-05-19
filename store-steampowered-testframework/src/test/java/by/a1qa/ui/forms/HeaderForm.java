package by.a1qa.ui.forms;

import by.a1qa.ui.elements.Button;
import by.a1qa.ui.elements.Dropdown;
import by.a1qa.utils.DriverUtils;
import by.a1qa.utils.Waiter;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

@AllArgsConstructor
public class HeaderForm {
    private Waiter waiter;
    private final Dropdown storeDropdown = new Dropdown("Store dropdown",
            By.xpath("//div[@class='content']//a[contains(@class,'supernav') and contains(@href,'store')]"));
    private final Button newsButton = new Button("News button in store dropdown",
            By.xpath("//div[contains(@class, 'supernav_content')]//a[contains(@href, 'news')]"));

    public void newsButtonClick() {
        waiter.waitForElementToBeClickable(storeDropdown);
        DriverUtils.navigateMouse(storeDropdown.getElement());
        waiter.waitForElementToBeClickable(newsButton);
        newsButton.click();
    }
}
