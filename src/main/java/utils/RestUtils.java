package utils;

import com.codeborne.selenide.WebDriverRunner;
import entities.User;
import io.restassured.RestAssured;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;


public class RestUtils {
    public void loginViaRest(String token, User user){
        Map<String,String> userCred = new HashMap<>();
        userCred.put("_token", "64T1kBRJwSMAIj19iYypDevXjKIDxiful49HdU1b");
        userCred.put("email", user.getEmail());
        userCred.put("password", user.getPassword());

        RestAssured.baseURI = "https://www.fashionette.de";

//        given()
//                .contentType("application/x-www-form-urlencoded")
//                .body(userCred)
//                .when().post("/account/authorise").then()
//                .statusCode(200);

        given().contentType("application/x-www-form-urlencoded").headers(userCred)
                .when().post("/account/authorise").then().statusCode(302);

//        given().contentType("application/x-www-form-urlencoded").headers(userCred)
//                .when().post("/account/authorise").getCookies();

        Map<String, String> cookies = given().contentType("application/x-www-form-urlencoded")
                .when().get("/account/customer").getCookies();

        Cookie ck = new Cookie("reference_id", cookies.get("reference_id"));
        Cookie ck1 = new Cookie("XSRF-TOKEN", cookies.get("XSRF-TOKEN"));
        Cookie ck2 = new Cookie("laravel_session", cookies.get("laravel_session"));

        WebDriverRunner.getWebDriver().manage().addCookie(ck);
        WebDriverRunner.getWebDriver().manage().addCookie(ck1);
        WebDriverRunner.getWebDriver().manage().addCookie(ck2);

        open("/account/customer");

}


}