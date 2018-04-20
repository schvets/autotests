package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.User;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.format;

public class RegisterPage {
    private ElementsCollection tableHeadersColumnName = $$(By.xpath("//table[@class='sortable']//thead"));

    private SelenideElement prefixLabel = $(By.id("ui-prefix"));
    private SelenideElement firstName = $(By.id("firstname"));
    private SelenideElement lastName = $(By.id("lastname"));
    private SelenideElement emailAddress = $(By.id("email_address"));
    private SelenideElement birthDay = $(By.id("day"));
    private SelenideElement birthMonthy = $(By.id("month"));
    private SelenideElement birthYear = $(By.id("year"));
    private SelenideElement password = $(By.id("password"));
    private SelenideElement passwordConfirmation = $(By.id("confirmation"));
    private SelenideElement newsletter = $x("//span[@class='ui-checkbox']");
    private SelenideElement confirmButton = $x("//button[contains(@class, 'primary-button')]");
    private String prefix = "//a[@role='option' and text()='%s']";



    public RegisterPage open() {
        Selenide.open("/customer/account/create/");
        return this;
    }

    public MyAccountPage registerNewUser(User user) {
        prefixLabel.click();
        $x(format(prefix, user.getPrefix())).click();
        firstName.setValue(user.getFirstName());
        lastName.setValue(user.getLastName());
        emailAddress.setValue(user.getEmail());
        birthDay.setValue(user.getBirthDay());
        birthMonthy.setValue(user.getBirthMonth());
        birthYear.setValue(user.getBirthYear());
        password.setValue(user.getPassword());
        passwordConfirmation.setValue(user.getPassword());
        passwordConfirmation.setValue(user.getPassword());
        if (user.getNewsletter()) {
            newsletter.click();
        }
        confirmButton.click();
        return new MyAccountPage();
    }

}
