import entities.Page;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.Test;
import utils.IConfigurationVariables;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class ApiTest {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    @Test
    public void addPageApiTest(){
        RestAssured.baseURI = configVariables.baseEndPoint();
        String[] featuresArr = {};

        Page page = Page.builder().title("test add page call").isTest(false).features(featuresArr).build();

        Response responce = RestAssured.given().body(page).contentType(ContentType.JSON).request().post("/api/pages");

        responce.then().statusCode(200);

        Page actualPage = responce.as(Page.class);

        assertReflectionEquals(page , actualPage);
    }

}