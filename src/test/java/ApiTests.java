import entities.Page;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.groups.Tuple;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testDataStorage.MultiVisionEntityStorage;
import utils.IConfigurationVariables;
import utils.RestUtilsMultiVision;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


public class ApiTests {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());
    RestUtilsMultiVision restUtilsMultiVision = new RestUtilsMultiVision();
    MultiVisionEntityStorage elementStorage = new MultiVisionEntityStorage();

    @BeforeTest
    public void setUp(){
        RestAssured.baseURI = configVariables.baseEndPoint();
    }

    @Test
    public void addPageApiTest(){
        Page expectedPage = elementStorage.getUniquePage();

        Response responce = restUtilsMultiVision.addPage(expectedPage);

        responce.then().statusCode(200);
        assertReflectionEquals(expectedPage , responce.as(Page.class));
    }

    @Test
    public void addPageApiTestMultiplyAssert(){
        Page expectedPage = elementStorage.getUniquePage();

        Response result = restUtilsMultiVision.addPage(expectedPage);

        result.then()
                .statusCode(200)
                .body("title", equalTo(expectedPage.getTitle()))
                .body("features", equalTo(expectedPage.getFeatures()))
                .body("isTest", equalTo(expectedPage.getIsTest()));
    }

    @Test
    public void deletePageTest(){
        Page expectedPage = elementStorage.getUniquePage();
        restUtilsMultiVision.addPage(expectedPage);
        Page pageForDelete = restUtilsMultiVision.getPageByTitle(expectedPage.getTitle());

        restUtilsMultiVision.deletePage(pageForDelete);

        assertThat(restUtilsMultiVision.getCurrentPagesList().contains(expectedPage))
                .isEqualTo(false)
                .as("page not deleted");
    }


    @DataProvider
    public Object[][] elementsProvider() {
        return new Object[][]{
                {elementStorage.getUniquePage(), elementStorage.getUniqueTestCase()},
                {elementStorage.getUniquePage(), elementStorage.getUniqueFeature()}
        };
    }
    @Test(dataProvider = "elementsProvider")
    public void addElementOnPageTest(Page expectedPage, Page expectedElement ){
        List<Page> expectedElementList = new ArrayList<>();
        expectedElementList.add(expectedElement);
        restUtilsMultiVision.addPage(expectedPage);
        Page pageForAddElement = restUtilsMultiVision.getPageByTitle(expectedPage.getTitle());

        restUtilsMultiVision.addTestCase(pageForAddElement,expectedElementList);

        Page actualPage = restUtilsMultiVision.getPageByTitle(expectedPage.getTitle());
        assertThat(actualPage.getFeatures())
                .extracting("title", "isTest", "features")
                .contains(Tuple.tuple(
                        expectedElementList.get(0).getTitle(),
                        expectedElementList.get(0).getIsTest(),
                        expectedElementList.get(0).getFeatures()));
    }
}

