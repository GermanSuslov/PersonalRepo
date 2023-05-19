package by.a1qa.ui.forms.pages;

import by.a1qa.ui.elements.Button;
import by.a1qa.ui.elements.Table;
import by.a1qa.ui.elements.TextBox;
import by.a1qa.ui.forms.SteamForm;
import by.a1qa.utils.ResourcesHelper;
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