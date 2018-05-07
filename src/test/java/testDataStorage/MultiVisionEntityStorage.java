package testDataStorage;

import entities.Page;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang.RandomStringUtils;
import utils.IConfigurationVariables;

import java.util.ArrayList;


public class MultiVisionEntityStorage {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public Page getUniquePage() {
        return Page.builder()
                .isTest(false)
                .title(RandomStringUtils.randomAlphanumeric(10))
                .features(new ArrayList<>())
                .build();
    }


    public Page getUniqueFeature() {
        return Page.builder()
                .isTest(false)
                .title(RandomStringUtils.randomAlphanumeric(10))
                .features(new ArrayList<>())
                .build();
    }

    public Page getUniqueTestCase() {
        return Page.builder()
                .isTest(true)
                .title(RandomStringUtils.randomAlphanumeric(10))
                .features(new ArrayList<>())
                .build();
    }


}
