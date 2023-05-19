package by.a1qa.ui.forms;

import by.a1qa.ui.elements.BaseElement;
import by.a1qa.utils.Browser;
import by.a1qa.utils.DriverUtils;
import by.a1qa.utils.ResourcesHelper;
import by.a1qa.utils.Waiter;
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