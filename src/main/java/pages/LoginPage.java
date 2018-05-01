package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.User;
import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class LoginPage {
    private SelenideElement userEmail = $(By.name("email"));
    private SelenideElement userPassword = $(By.name("password"));
    private SelenideElement acceptButton = $x("//div[@class='btn-default form__login__submit form__submit']");
    private SelenideElement errorLabel = $x("//div[@class='login__errortext login--modal__response-error']");
    private SelenideElement token = $x("//input[@name='_token']");
    private SelenideElement emailError = $(byId("email-error"));
    private SelenideElement passwordError = $(byId("password-error"));


    public LoginPage open() {
        Selenide.open("/login");
        return this;
    }

    public MyAccountPage loginAs(User user) {
        userEmail.setValue(user.getEmail());
        userPassword.setValue(user.getPassword());
        acceptButton.click();
        return new MyAccountPage();
    }

    public void blockAccount(User user) {
        int counter = 5;
        while (counter > 0) {
            if (isModalVisible()) {
                new ModalPage().getCloseModal().click();
            }
            loginAs(user);
            counter--;
        }
    }

    private boolean isModalVisible() {
        return new ModalPage().getModalBody().isDisplayed();
    }

    public String getToken() {
        return token.getValue();
    }

    public String getEmailError() {
        return emailError.getText();
    }

    public String getPasswordError() {
        return passwordError.getText();
    }

}
