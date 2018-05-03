package utils;

import entities.Page;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;

public class RestUtilsMultiVision {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public Response addPage(Page page){
        return RestAssured.given()
                .body(page)
                .contentType(ContentType.JSON)
                .request()
                .post(configVariables.addPagePath());
    }
}
