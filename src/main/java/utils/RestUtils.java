package utils;

import com.codeborne.selenide.WebDriverRunner;
import entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RestUtils {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public void loginViaRest(String token, User user) {
        RestAssured.baseURI = configVariables.baseUrl();

        RestAssured.given().contentType(ContentType.URLENC).
                formParam("_token", token)
                .formParam("email", user.getEmail())
                .formParam("password", user.getPassword())
                .cookies(getCookiesWd())
                .request()
                .post(configVariables.authorisePath());
    }


    public static Map<String, String> getCookiesWd(){
        Map<String, String> result = new HashMap<>();
        Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        for (Cookie cookie : cookies) {
            result.put(cookie.getName(), cookie.getValue());
        }
        return result;
    }


    public String getTokenViaRest() {
        Response response = RestAssured.given().get(configVariables.baseUrl());
        String responseBody = response.getBody().asString();
        String pattern = "window.csrfToken = ";
        int patternLen = pattern.length();
        final int tokenLen = 42;
        return responseBody.substring(
                responseBody.indexOf(pattern) + patternLen,
                responseBody.indexOf(pattern) + patternLen + tokenLen)
                .replace("'","");
    }


    public void addProductToCart(String productSku) {
        RestAssured.baseURI = configVariables.baseUrl();

        RestAssured.given().contentType(ContentType.ANY).get(configVariables.productPath()).cookies();

        Response responce = RestAssured.given().contentType(ContentType.URLENC)
                .formParam("_token", getTokenViaRest())
                .formParam("super_attribute%5B261%5D", productSku)
                .cookies(getCookiesWd())
                .request()
                .post(configVariables.productPath());

        responce.getCookies();
        responce.getStatusCode();


    }

    public Map<String, String> getCookiesRest() {
        RestAssured.baseURI = configVariables.baseUrl();
        return RestAssured.given().contentType(ContentType.TEXT).get(configVariables.customerPath()).getCookies();
    }
}