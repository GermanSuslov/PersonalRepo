package by.me.ui.forms.pages;

import by.me.ui.elements.Button;
import by.me.ui.elements.Table;
import by.me.ui.elements.TextBox;
import by.me.ui.forms.SteamForm;
import by.me.utils.ResourcesHelper;
import org.openqa.selenium.By;

import java.util.List;

public class PrivacyAgreementPage extends SteamForm {
    private final TextBox privacyText = new TextBox("Privacy policy text",
            By.xpath("//*[@id='newsColumn']"));
    private final String LANGUAGE_BUTTON_NAME = "Language picture button";

    public PrivacyAgreementPage() {
        super(new Table("Bar with language elements list",
                By.xpath("//*[@id='languages']")));
    }

    public boolean languagesAreSupported() {
        List<String> languages = ResourcesHelper.getLanguagesTestData();
        boolean languagesAreSupported = languages.stream()
                .map(language -> By.xpath("//a[contains(@href, '" + language + "')]"))
                .map(byLanguageButton -> new Button(LANGUAGE_BUTTON_NAME, byLanguageButton))
                .allMatch(Button::isEnabled);
        return languagesAreSupported;
    }

    public boolean policyRevisionSignIsCorrect(Integer revisionSignYear) {
        return privacyText.containsText(revisionSignYear.toString());
    }
}