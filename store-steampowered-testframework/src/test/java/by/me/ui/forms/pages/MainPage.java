package by.me.ui.forms.pages;

import by.me.ui.elements.Image;
import by.me.ui.forms.NavigationForm;
import by.me.ui.forms.SteamForm;
import by.me.utils.DriverUtils;
import by.me.utils.ResourcesHelper;
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