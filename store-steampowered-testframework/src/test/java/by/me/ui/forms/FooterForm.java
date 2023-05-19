package by.me.ui.forms;

import by.me.ui.elements.Button;
import by.me.utils.DriverUtils;
import by.me.utils.Waiter;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;

@AllArgsConstructor
public class FooterForm {
    private Waiter waiter;
    private final Button privacyLinkButton = new Button("Privacy link",
            By.xpath("//div[@id='footer_text']//a[contains(@href, 'privacy_agreement')]"));

    public void privacyLinkClick() {
        DriverUtils.scrollPageToBottom();
        waiter.waitForElementToBeClickable(privacyLinkButton);
        privacyLinkButton.click();
    }
}