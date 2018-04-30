import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import testDataStorage.UserStorage;
import utils.IConfigurationVariables;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiTest {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());
    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();


    @BeforeMethod
    public void setUp() {
        Configuration.baseUrl = configVariables.baseUrl();
        Configuration.browser = configVariables.currentBrowser();
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
    }

    @Test
    public void postLoginFormPositive()  {
        loginPage.open();
        RestAssured.baseURI = configVariables.baseUrl();

        Response response = RestAssured.given().contentType(ContentType.URLENC).
                formParam("_token", loginPage.getToken())
                .formParam("email", new UserStorage().getRealUser().getEmail())
                .formParam("password", new UserStorage().getRealUser().getPassword())
                .request()
                .post(configVariables.authorisePath());

        response.then().assertThat().body("title", containsString("Redirecting to https://www.fashionette.de"));
        response.then().statusCode(302);


//        https://www.fashionette.de/account/authorise?_token=woNX6kQQoaPO3hfiO2DyNwrsLlFHot8vFI51Pkhg&email=fashionetteTestUserAcc%40gmail.com&password=fashionette06027777


        Response response2 = RestAssured.given().header("Content-Type", ContentType.TEXT)
                .request().get(configVariables.customerPath());

        response2.then().assertThat().body("title", equalTo("Designertaschen und Accessoires von FASHIONETTE"));
        response2.then().statusCode(200);


    }

    @Test
    public void postLoginFormNegative()  {
        open("/");
        String token = $x("//div[@id='box--newsletter--dynamic-content']//input[@name='_token']").getValue();
        RestAssured.baseURI = "https://www.fashionette.de";

        Response response = RestAssured.given().header("Content-Type", "application/x-www-form-urlencoded").
                formParam("_token", "token")
                .formParam("email", new UserStorage().getRealUser().getEmail())
                .formParam("password", "111111")
                .request().post(configVariables.authorisePath());

        response.then().assertThat().body("title", equalTo("Redirecting to https://www.fashionette.de"));
        response.then().statusCode(302);


        Response response2 = RestAssured.given().header("Content-Type", ContentType.TEXT)
                .request().get(configVariables.customerPath());

        response2.then().assertThat().body("title", equalTo("Designertaschen und Accessoires von FASHIONETTE"));
        response2.then().statusCode(200);
    }
}

