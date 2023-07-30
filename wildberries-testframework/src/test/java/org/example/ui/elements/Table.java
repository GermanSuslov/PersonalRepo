package org.example.ui.elements;

public class Table extends BaseElement {
    public Table(String elementName, String locator) {
        super(elementName, locator);
    }

    public boolean isHidden() {
        return !getSelenideElement().isDisplayed();
    }
    public Integer getTableArea() {
        String size = String.valueOf(getSelenideElement().getSize());
        size = size.replaceAll("[(),]", "");
        String[] sizes = size.split(" ");
        Integer area = Integer.parseInt(sizes[0]) * Integer.parseInt(sizes[1]);
        return area;
    }
}
