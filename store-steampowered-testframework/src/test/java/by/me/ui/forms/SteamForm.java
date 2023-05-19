package by.me.ui.forms;

import by.me.ui.elements.BaseElement;

public abstract class SteamForm extends BaseForm {
    public final FooterForm footerForm;
    public final HeaderForm headerForm;

    public SteamForm(BaseElement uniqueElement) {
        super(uniqueElement);
        footerForm = new FooterForm(waiter);
        headerForm = new HeaderForm(waiter);
    }
}