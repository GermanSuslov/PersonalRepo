package by.me.ui.forms;

import by.me.ui.elements.BaseElement;
import by.me.utils.Browser;
import by.me.utils.DriverUtils;
import by.me.utils.ResourcesHelper;
import by.me.utils.Waiter;
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