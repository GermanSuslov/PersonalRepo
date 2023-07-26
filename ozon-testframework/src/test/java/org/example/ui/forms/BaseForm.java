package org.example.ui.forms;

import org.example.ui.utils.DataHelper;
import org.example.ui.utils.Waiter;

public class BaseForm {
    private static Waiter waiter;
    public Waiter getWaiter() {
        if(waiter == null) {
            return Waiter.getWaiter(DataHelper.getWaitTime());
        }
        else return waiter;
    }
}
