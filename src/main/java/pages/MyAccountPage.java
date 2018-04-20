package pages;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import entities.User;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

@Getter
public class MyAccountPage {
    private SelenideElement conformationLabel = $(byClassName("success-msg"));
    private ElementsCollection userData = $$x("//*[@class='box-content responsive-slide']//dt[contains(text(),'ID')]//..//dd");

    public User getUserData(){
        List<String> userDataValue = Arrays.asList(userData.get(1).getText().split(" "));
        return User.builder().prefix(userDataValue.get(0))
                .firstName(userDataValue.get(1))
                .lastName(userDataValue.get(2)).build();
    }



}
