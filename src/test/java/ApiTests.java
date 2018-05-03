import entities.Feature;
import entities.Page;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.IConfigurationVariables;
import utils.RestUtilsMultiVision;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class ApiTests {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());
    RestUtilsMultiVision restUtilsMultiVision = new RestUtilsMultiVision();


    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = configVariables.baseEndPoint();
    }

    @Test
    public void addPageApiTest(){
        List<Feature> featuresArr = new ArrayList<>();
        Page expectedPage = Page.builder().title("test add page call").isTest(false).features(featuresArr).build();

        Response responce = restUtilsMultiVision.addPage(expectedPage);

        responce.then().statusCode(200);
        Page actualPage = responce.as(Page.class);
        assertReflectionEquals(expectedPage , actualPage);
    }

    @Test
    public void addPageApiTestMultiplyAssert(){
        List<Feature> featuresArr = new ArrayList<>();
        Page expectedPage = Page.builder().title("test add page call").isTest(false).features(featuresArr).build();

        restUtilsMultiVision
                .addPage(expectedPage)
                .then()
                .statusCode(200)
                .body("title", equalTo(expectedPage.getTitle()))
                .body("features", equalTo(expectedPage.getFeatures()))
                .body("isTest", equalTo(expectedPage.getIsTest()));
    }

    @Test
    public void addPageApiTestMultiplyккAssert(){
        String result = RestAssured.given().get("/api/pages").thenReturn().body().asString();
//        Page data = new Gson().fromJson(result, Page.class);

//        Type collectionType = new TypeToken<Collection<Page>>(){}.getType();
//        Collection<Page> data = new Gson().fromJson(result, collectionType);


    }


//Request URL: http://localhost:3030/api/pages?__v=0&_id=5aeb78bc41a98c55cfafe679&isTest=false&title=Page.9
//
//Request URL: http://localhost:3030/tree
//Request Method: GET
//Status Code: 304 Not Modified
//Remote Address: [::1]:3030
//Referrer Policy: no-referrer-when-downgrade
//
//Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//Accept-Encoding: gzip, deflate, br
//Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,uk;q=0.6
//Cache-Control: max-age=0
//Connection: keep-alive
//Host: localhost:3030
//If-None-Match: W/"c1b-jmB602rdg3W+6hAJ+sY4geZnA9s"
//Upgrade-Insecure-Requests: 1
//User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36

}