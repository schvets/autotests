package pages;


import com.codeborne.selenide.SelenideElement;
import entities.User;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class MyAccountPage {
    private SelenideElement conformationLabel = $(byClassName("success-msg"));
    private SelenideElement accountPersonaldataShow = $(byClassName("account-personaldata"));
    private SelenideElement userData = $x("(//div[@class='account__content__column account__content__column--customer'])[1]");



    public User getUserData(){
        accountPersonaldataShow.click();
        List<String> userDataValue = Arrays.asList(userData.getText().split(" "));
        return User.builder().prefix(userDataValue.get(0))
                .firstName(userDataValue.get(1))
                .lastName( Arrays.asList(userDataValue.get(2).split("\n")).get(0)).build();
    }

}
