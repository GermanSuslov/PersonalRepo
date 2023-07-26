package org.example.ui.forms.pages;

import org.example.ui.elements.Image;
import org.example.ui.forms.NavigationForm;
import org.example.ui.forms.SteamForm;
import org.example.utils.DriverUtils;
import org.example.utils.ResourcesHelper;
import org.openqa.selenium.By;

public class MainPage extends SteamForm {
    public final NavigationForm navigationForm;

    public MainPage() {
        super(new Image("Gift cards image",
                By.xpath("//a[contains(@href, 'digitalgiftcards')]//img")));
        navigationForm = new NavigationForm(waiter);
    }

    public void open() {
        DriverUtils.navigate(ResourcesHelper.getStartUrl());
    }
}