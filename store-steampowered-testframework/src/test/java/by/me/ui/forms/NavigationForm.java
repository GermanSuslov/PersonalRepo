package by.me.ui.forms;

import by.me.ui.elements.Button;
import by.me.ui.elements.Dropdown;
import by.me.utils.DriverUtils;
import by.me.utils.Waiter;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

@AllArgsConstructor
public class NavigationForm {
    private Waiter waiter;
    private final Dropdown newAndNoteworthyDropdown = new Dropdown("New & Noteworthy dropdown",
            By.xpath("//div[@id='noteworthy_tab']"));
    private final Dropdown dropdownFlyout = new Dropdown("New & Noteworthy dropdown flyout",
            By.xpath("//div[@id='noteworthy_flyout']"));
    private final Button mostPlayedButton = new Button("Most played button in a dropdown",
            By.xpath("//a[contains(@href, 'mostplayed')]"));

    public NavigationForm newAndNoteworthyDropdownNavigateMouse() {
        DriverUtils.navigateMouse(newAndNoteworthyDropdown.getElement());
        waiter.waitForElementToBeVisible(dropdownFlyout);
        return this;
    }

    public void mostPlayedButtonClick() {
        waiter.waitForElementToBeClickable(mostPlayedButton);
        mostPlayedButton.click();
    }
}