package org.example.ui.forms.pages;

import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.example.ui.forms.FilterForm;
import org.openqa.selenium.By;

public class CategoryPage extends BaseForm {
    public CatalogForm catalogForm;
    public FilterForm filterForm;

    public CategoryPage(String title) {
        this.catalogForm = new CatalogForm();
        this.filterForm = new FilterForm();
        super.uniqueElement = new Title("Заголовок - " + title,
                String.format("//h1[contains(text(),'%s')]", title));
    }

}
