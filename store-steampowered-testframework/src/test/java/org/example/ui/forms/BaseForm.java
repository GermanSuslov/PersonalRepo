package org.example.ui.forms;

import org.example.ui.elements.BaseElement;
import org.example.utils.Browser;
import org.example.utils.DriverUtils;
import org.example.utils.ResourcesHelper;
import org.example.utils.Waiter;
import lombok.Getter;

public abstract class BaseForm {
    protected Waiter waiter;
    @Getter
    private final BaseElement uniqueElement;

    public BaseForm(BaseElement uniqueElement) {
        this.waiter = Waiter.getWaiter(Browser.getDriver(), ResourcesHelper.getWaitTime());
        this.uniqueElement = uniqueElement;
    }

    public boolean isOpened() {
        waiter.waitForElementToBeVisible(uniqueElement);
        return uniqueElement.isDisplayed();
    }

    public boolean openedInNewTab() {
        int expectedNumberOfWindows = 2;
        return DriverUtils.getNumberOfWindows() == expectedNumberOfWindows;
    }
}