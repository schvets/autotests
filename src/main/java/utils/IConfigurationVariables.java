package utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
		"classpath:config.properties",
        "classpath:testData.properties"
})

public interface IConfigurationVariables extends Config{
    String currentBrowser();
    String baseUrl();
    String realUserPrefix();
    String realUserFirstName();
    String realUserLastName();
    String realUserEmail();
    String realUserBirthDate();
    String realUserPassword();
    String realUserEmailPassword();

    String blockUserPrefix();
    String blockUserFirstName();
    String blockUserLastName();
    String blockUserEmail();
    String blockUserBirthDate();
    String blockUserPassword();

    String authorisePath();
    String customerPath();
}