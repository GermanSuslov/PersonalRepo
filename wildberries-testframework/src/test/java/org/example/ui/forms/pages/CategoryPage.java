package org.example.ui.forms.pages;

import org.example.ui.elements.Table;
import org.example.ui.elements.Title;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.CatalogForm;
import org.openqa.selenium.By;

public class CategoryPage extends BaseForm {
    public CatalogForm catalogForm;
    private final Title uniqueElement = new Title("Заголовок страницы категории",
            By.xpath("//div[contains(@data-block-type, 'main')]"));

    public CategoryPage() {
        super.uniqueElement = uniqueElement;
        this.catalogForm = new CatalogForm();
    }

}
