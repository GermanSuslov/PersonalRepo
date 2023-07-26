package org.example.ui.forms;

import org.example.ui.elements.BaseElement;

public abstract class SteamForm extends BaseForm {
    public final FooterForm footerForm;
    public final HeaderForm headerForm;

    public SteamForm(BaseElement uniqueElement) {
        super(uniqueElement);
        footerForm = new FooterForm(waiter);
        headerForm = new HeaderForm(waiter);
    }
}