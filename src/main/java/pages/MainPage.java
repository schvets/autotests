package pages;

import com.codeborne.selenide.Selenide;

public class MainPage {
    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    public HeaderPage getHeader() {
        return new HeaderPage();
    }

}
