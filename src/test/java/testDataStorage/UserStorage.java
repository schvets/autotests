package testDataStorage;

import entities.User;
import org.aeonbits.owner.ConfigFactory;
import utils.IConfigurationVariables;

import java.util.*;


public class UserStorage {
    IConfigurationVariables configVariables = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public User getUniqueUser() {
        Random rand = new Random();
        return User.builder()
                .prefix("Mr.")
                .firstName(UUID.randomUUID().toString())
                .lastName(UUID.randomUUID().toString())
                .email(new StringBuilder().append(UUID.randomUUID()).append("@gmail.com").toString())
                .birthDay(String.valueOf(rand.nextInt(30)))
                .birthMonth(String.valueOf(rand.nextInt(12)))
                .birthYear(String.valueOf(1900 + rand.nextInt((1999 - 1900) + 1)))
                .password(UUID.randomUUID().toString())
                .newsletter(true)
                .build();
    }

    public User getRealUser() {
        List<String> birthDate = Arrays.asList(configVariables.realUserBirthDate().split("/"));
        return User.builder()
                .prefix(configVariables.realUserPrefix())
                .firstName(configVariables.realUserFirstName())
                .lastName(configVariables.realUserLastName())
                .email(configVariables.realUserEmail())
                .birthDay(birthDate.get(0))
                .birthMonth(birthDate.get(1))
                .birthYear(birthDate.get(2))
                .password(configVariables.realUserPassword())
                .newsletter(false)
                .build();
    }

    public User getRealUserForBlock() {
        List<String> birthDate = Arrays.asList(configVariables.realUserBirthDate().split("/"));
        return User.builder()
                .prefix(configVariables.realUserPrefix())
                .firstName(configVariables.realUserFirstName())
                .lastName(configVariables.realUserLastName())
                .email(configVariables.blockUserEmail())
                .birthDay(birthDate.get(0))
                .birthMonth(birthDate.get(1))
                .birthYear(birthDate.get(2))
                .password(configVariables.blockUserPassword())
                .newsletter(false)
                .build();
    }
}
