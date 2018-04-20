package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.User;
import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class LoginPage {
    private SelenideElement userEmail = $(By.id("email"));
    private SelenideElement userPassword = $(By.id("pass"));
    private SelenideElement acceptButton = $x("//div[@class='hidden-xs col2-set']//button[@id='send2']");
    private SelenideElement errorLabel = $(byClassName("error-msg"));


    public LoginPage open() {
        Selenide.open("/customer/account/login/");
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
}
