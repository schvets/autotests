package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CartPage {
    private SelenideElement prefixLabel = $(By.id("ui-prefix"));
    private SelenideElement itemBlock = $x("//a[@data-sku]//div[@class='product--list__item__description']"),
    itemProducer = itemBlock.$x(".//h3"),
    itemDesc = itemBlock.$x(".//p"),
    itemPrice = itemBlock.$x(".//span/p");

    public CartPage open() {
        Selenide.open("/cart");
        return this;
    }

    public String getItemProducer() {
        return itemProducer.getText();
    }

    public String getItemDesc() {
        return itemDesc.getText();
    }

    public String getItemPrice() {
        return itemPrice.getText();
    }
}
