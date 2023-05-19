package by.a1qa.ui.forms;

import by.a1qa.ui.elements.Button;
import by.a1qa.utils.DriverUtils;
import by.a1qa.utils.Waiter;
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