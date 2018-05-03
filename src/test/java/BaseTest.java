import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import entities.Menu;
import entities.User;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import testDataStorage.UserStorage;
import utils.IConfigurationVariables;
import utils.RestUtilsFashion;
import utils.SelenoidWebDriverProvider;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.title;
import static org.assertj.core.api.Assertions.assertThat;


public class BaseTest {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private final RegisterPage registerPage = new RegisterPage();
    private final MyAccountPage myAccountPage = new MyAccountPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final RestUtilsFashion restUtils = new RestUtilsFashion();
    private final CartPage cartPage = new CartPage();


    @BeforeMethod (alwaysRun = true)
    public void setUp() {
        Configuration.browser = SelenoidWebDriverProvider.class.getName();
        Configuration.baseUrl = configVariables.baseUrl();
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();

    }

    @Test(enabled = false)
    public void registerNewUserTest() {
        registerPage.open();
        registerPage.registerNewUser(new UserStorage().getUniqueUser());
        myAccountPage.getConformationLabel().shouldBe(visible);
    }

    @Test
    public void loginUserTestPositive() {
        loginPage.open();
        User expectedUser = new UserStorage().getRealUser();
        loginPage.loginAs(expectedUser);
        User actualUser = myAccountPage.getUserData();
        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(expectedUser.getPrefix()).isEqualTo(actualUser.getPrefix())
//                .describedAs("incorrect user sex");
        softly.assertThat(expectedUser.getFirstName()).isEqualTo(actualUser.getFirstName())
                .describedAs("incorrect user first name");
        softly.assertThat(expectedUser.getLastName()).isEqualTo(actualUser.getLastName())
                .describedAs("incorrect user second name");
        softly.assertAll();
    }

    @Test
    public void loginUserTestPositiveViaRest() {
        loginPage.open();
        User expectedUser = new UserStorage().getRealUser();
        restUtils.loginViaRest(loginPage.getToken(),expectedUser);
        refresh();
        User actualUser = myAccountPage.getUserData();
        SoftAssertions softly = new SoftAssertions();
//        softly.assertThat(expectedUser.getPrefix()).isEqualTo(actualUser.getPrefix())
//                .describedAs("incorrect user sex");
        softly.assertThat(expectedUser.getFirstName()).isEqualTo(actualUser.getFirstName())
                .describedAs("incorrect user first name");
        softly.assertThat(expectedUser.getLastName()).isEqualTo(actualUser.getLastName())
                .describedAs("incorrect user second name");
        softly.assertAll();
    }

    @DataProvider
    public Object[][] incorectCredential() {
        return new Object[][]{
                {User.builder().email("incorectMail@gmail.com").password("1111111").build()},
                {User.builder().email(new UserStorage().getRealUser().getEmail()).password("1111111").build()}
        };
    }

    @Test(dataProvider = "incorectCredential")
    public void loginUserTestNegative(User user) {
        loginPage.open();
        loginPage.loginAs(user);
        loginPage.getErrorLabel().shouldBe(visible);
        assertThat(loginPage.getErrorLabel().getText())
                .isEqualToIgnoringCase("Sorry, das hat leider nicht geklappt. " +
                        "Bitte überprüfe E-Mail und Passwort. Aus Sicherheitsgründen werden Konten " +
                        "nach drei erfolglosen Versuchen für fünf Minuten gesperrt. Das Zurücksetzen" +
                        " eines Passworts ist während einer solchen Sperrung leider nicht möglich.")
                .describedAs("wrong error message");
    }

    @Test(enabled = false)
    public void loginUserTestBlock() {
        loginPage.open();
        loginPage.blockAccount(User.builder().email(new UserStorage().getRealUserForBlock()
                .getEmail()).password("1111111").build());
        loginPage.getErrorLabel().shouldBe(visible);
        assertThat(loginPage.getErrorLabel().getText())
                .isEqualToIgnoringCase("You have 4 times entered a wrong password! For security " +
                        "reason we deactivate your account for 5 min. You can login again after " +
                        "the time is over.").describedAs("wrong error block message");
    }

    @DataProvider
    public Object[][] menuLevels() {
        return new Object[][]{
                {Menu.builder().level("PASSEND ZUR TASCHE").level("Taschen Accessoires")
                        .pageUrl("https://www.fashionette.de/taschen-accessoires")
                        .title("Die schönsten Taschen-Accessoires versandkostenfrei | fashionette").build()},
                {Menu.builder().level("DESIGNER").level("Hugo").pageUrl("https://www.fashionette.de/hugo")
                        .title("Hugo Boss Taschen & Accessoires versandkostenfrei | fashionette").build()}
        };
    }

    @Test(dataProvider = "menuLevels", enabled = false)
    public void menuTransitionTest(Menu menu) {
        mainPage.open();
        mainPage.getHeader().goTo(menu);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(menu.getTitle()).isEqualTo(title())
                .describedAs("incorrect page title");
        softly.assertThat(menu.getPageUrl()).isEqualTo(WebDriverRunner.url())
                .describedAs("incorrect page url");
        softly.assertAll();
    }


    @DataProvider
    public Object[][] loginFormValidation() {
        return new Object[][]{
                {User.builder().email("").password("").build(),
                        "Bitte gib Deine E-Mail-Adresse ein.","Bitte gib Dein Passwort ein."},
                {User.builder().email("1").password("1").build(),
                        "Bitte gib Deine E-Mail-Adresse im richtigen Format ein.",
                        "Das Passwort muss mindestens 8 Zeichen lang sein und mindestens eine Zahl enthalten."}
        };
    }


    @Test(dataProvider = "loginFormValidation")
    public void validationTestFront(User user, String expMsgLogin, String expMsgPassword ) {
        loginPage.open();
        loginPage.loginAs(user);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(loginPage.getEmailError()).isEqualTo(expMsgLogin)
                .describedAs("incorrect Email Error");
        softly.assertThat(loginPage.getPasswordError()).isEqualTo(expMsgPassword)
                .describedAs("incorrect Password Error");
        softly.assertAll();

    }

    @Test
    public void addToProductCartViaRestTest() {
        loginPage.open();
        User expectedUser = new UserStorage().getRealUser();
        restUtils.loginViaRest(loginPage.getToken(),expectedUser);
        refresh();
        restUtils.addProductToCart("913" );
        cartPage.open();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartPage.getItemProducer()).isEqualToIgnoringCase("MICHAEL KORS")
                .describedAs("incorrect Producer");
        softly.assertThat(cartPage.getItemDesc()).isEqualToIgnoringCase("BLAKELY MEDIUM BUCKET BAG TILE BLUE")
                .describedAs("incorrect Description");
        softly.assertThat(cartPage.getItemPrice()).isEqualTo("394 €")
                .describedAs("incorrect price");
        softly.assertAll();
    }

}
