package pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class ModalPage {
    private final SelenideElement modalBody = $x
            ("//div[@id='simplemodal-container']//div[@id='newsletter" +
            "-popup-subscription']"),
            subscribeEmailInput = modalBody.$(byId("newsletter-popup")),
            subscribeButton = modalBody.$(byId("popup-newsletter-button"));
    private final SelenideElement closeModal = $(byId("newsletter-close"));

}
