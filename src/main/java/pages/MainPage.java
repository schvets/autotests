package pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    public HeaderPage getHeader() {
        return new HeaderPage();
    }

    public String getToken() {
       return $x("//div[@id='box--newsletter--dynamic-content']//input[@name='_token']").getValue();
    }


}
