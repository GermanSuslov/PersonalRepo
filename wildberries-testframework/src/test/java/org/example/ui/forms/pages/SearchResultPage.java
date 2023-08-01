package org.example.ui.forms.pages;

import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;

public class SearchResultPage extends BaseForm {
    public SearchResultPage(String title) {
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }
}
