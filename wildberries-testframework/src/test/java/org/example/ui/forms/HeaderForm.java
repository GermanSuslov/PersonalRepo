package org.example.ui.forms;

import org.example.ui.elements.Button;

public class HeaderForm extends BaseForm {
    public CatalogForm catalogForm;
    public SearchForm searchForm;
    public Button mainPageButton = new Button("Кнопка главной страницы",
            "//a[@data-wba-header-name = 'Main']");

    public HeaderForm() {
        super.uniqueElement = mainPageButton;
        this.catalogForm = new CatalogForm();
        this.searchForm = new SearchForm();
    }
}
