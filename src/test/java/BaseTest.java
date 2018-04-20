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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.*;


public class BaseTest {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private final RegisterPage registerPage = new RegisterPage();
    private final MyAccountPage myAccountPage = new MyAccountPage();
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final ModalPage modalPage = new ModalPage();


    @BeforeMethod
    public void setUp() {
        Configuration.baseUrl = configVariables.baseUrl();
        Configuration.browser = configVariables.currentBrowser();
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
    }

    @Test
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
        softly.assertThat(expectedUser.getPrefix()).isEqualTo(actualUser.getPrefix())
                .describedAs("incorrect user sex");
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
                .isEqualToIgnoringCase("Indirizzo e-mail o password non validi")
                .describedAs("wrong error message");
    }

    @Test
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
                {Menu.builder().level("Designer").pageUrl("https://www.fashionette.it/designer")
                        .title("Le firme più amate: ordina borse online — Fashionette").build()},
                {Menu.builder().level("Categorie prodotti").level("Sneaker")
                        .pageUrl("https://www.fashionette.it/sneakers-1")
                        .title("Sneaker firmate online|Collezioni attuali 2017 — Fashionette").build()}
        };
    }

    @Test(dataProvider = "menuLevels")
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
}