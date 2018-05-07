package utils;

import entities.Page;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestUtilsMultiVision {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public Response addPage(Page page){
        return RestAssured.given()
                .body(page)
                .contentType(ContentType.JSON)
                .request()
                .post(configVariables.pagePath());
    }

    public Response deletePage(Page page){
        return RestAssured.given()
                .queryParam("__v", page.get__v())
                .queryParam("_id", page.get_id())
                .queryParam("isTest", page.getIsTest())
                .queryParam("title", page.getTitle())
                .contentType(ContentType.JSON)
                .request()
                .delete(configVariables.pagePath());
    }

    public List<Page> getCurrentPagesList(){
        return  Arrays.stream(
                RestAssured.given()
                        .get(configVariables.pagePath())
                        .as(Page[].class))
                .collect(Collectors.toList());
    }

    public Page getPageByTitle(String pageTitle) {
        return getCurrentPagesList()
                .stream()
                .filter(el ->el.getTitle().equals(pageTitle))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no page with title " + pageTitle));

    }

    public Response addFeature(Page page, List<Page> featureList){
        page.setFeatures(featureList);
        return RestAssured.given()
                .body(page)
                .contentType(ContentType.JSON)
                .request()
                .put(configVariables.pagePath());
    }


    public Response addTestCase(Page page, List<Page> testList){
        page.setFeatures(testList);
        return RestAssured.given()
                .body(page)
                .contentType(ContentType.JSON)
                .request()
                .put(configVariables.pagePath());
    }
}