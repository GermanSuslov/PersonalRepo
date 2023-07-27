package org.example.ui.forms;

import org.example.ui.elements.BaseElement;
import org.example.ui.utils.Browser;
import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;

public abstract class BaseForm {
    protected BaseElement uniqueElement;
    public abstract void open();
    public boolean isOpen() {
        Waiter.getWaiter().waitForElementToBeVisible(uniqueElement);
        return uniqueElement.isDisplayed();
    }
}
