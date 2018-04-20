package entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String prefix;
    String firstName;
    String lastName;
    String email;
    String birthDay;
    String birthMonth;
    String birthYear;
    String password;
    Boolean newsletter;
}
