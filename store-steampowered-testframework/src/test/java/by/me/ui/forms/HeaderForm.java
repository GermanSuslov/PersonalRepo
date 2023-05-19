package by.me.ui.forms;

import by.me.ui.elements.Button;
import by.me.ui.elements.Dropdown;
import by.me.utils.DriverUtils;
import by.me.utils.Waiter;
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
