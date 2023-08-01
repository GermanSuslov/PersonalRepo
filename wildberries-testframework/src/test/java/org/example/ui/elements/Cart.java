package org.example.ui.elements;

public class Cart extends BaseElement {
    public Cart(String elementName, String locator) {
        super(elementName, locator);
    }
    public Integer getBorderArea() {
        String size = String.valueOf(getSelenideElement().getSize());
        size = size.replaceAll("[(),]", "");
        String[] sizes = size.split(" ");
        Integer area = Integer.parseInt(sizes[0]) * Integer.parseInt(sizes[1]);
        return area;
    }

    public Integer getId() {
        String id = getSelenideElement().getAttribute("data-nm-id");
        return Integer.parseInt(id);
    }
}
