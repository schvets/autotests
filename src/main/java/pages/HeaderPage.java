package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.Menu;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class HeaderPage {
    private ElementsCollection menuLabels = $$x("//*//a");
    private SelenideElement header = $(By.id("header")),
            searchInput = header.$(By.id("ui-id-1")),
            titleLogo = header.$x("//*[@class='header-left']//img"),
            logOutLink =header.$(By.className("first")),
            myAccountLink = header.$x("(//div[@class='links']//li)[2]"),
            helpLink = header.$x("(//div[@class='links']//li)[3]"),
            basketButton = $(By.className("basket-icon"));

    public void goTo(Menu menu) {
        menu.getLevels().forEach(el -> menuLabels
                .filter(Condition.visible)
                .find(Condition.exactText(el))
                .click());
    }
}
