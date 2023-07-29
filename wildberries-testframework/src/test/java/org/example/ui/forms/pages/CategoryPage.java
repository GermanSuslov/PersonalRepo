package org.example.ui.forms.pages;

import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.openqa.selenium.By;

public class CategoryPage extends BaseForm {
    public CatalogForm catalogForm;
    private Title uniqueElement;

    public CategoryPage(String title) {
        uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
        this.catalogForm = new CatalogForm();
        super.uniqueElement = uniqueElement;
    }

}
