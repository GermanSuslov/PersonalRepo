package org.example.ui.forms.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.elements.Table;
import org.example.ui.forms.BaseForm;
import org.example.ui.forms.HeaderForm;
import org.example.ui.utils.Waiter;

import java.util.List;

public class SellersPage extends BaseForm {
    public HeaderForm headerForm;
    private final Table sellerTable = new Table("Таблица с инфо о продавце",
            "//div[contains(@class, 'catalog-page__seller-details')]");
    private final Table sellersInfoTables = new Table("Элементы с информацией о продавце",
            "//div[contains(@class, 'seller-details__parameter-item')]");

    public SellersPage() {
        this.headerForm = new HeaderForm();
        super.uniqueElement = sellerTable;
    }

    public boolean sellersInfoTablesIsDisplayed() {
        List<SelenideElement> infoList = sellersInfoTables.getElements();
        Waiter.getWaiter().waitForElementsToBeVisible(infoList);

        return infoList.size() == 4;
    }
}
