package by.a1qa.ui.forms.pages;

import by.a1qa.ui.elements.Image;
import by.a1qa.ui.forms.NavigationForm;
import by.a1qa.ui.forms.SteamForm;
import by.a1qa.utils.DriverUtils;
import by.a1qa.utils.ResourcesHelper;
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