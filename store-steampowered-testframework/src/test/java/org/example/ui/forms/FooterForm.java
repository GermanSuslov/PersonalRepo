package org.example.ui.forms;

import org.example.ui.elements.Button;
import org.example.utils.DriverUtils;
import org.example.utils.Waiter;
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