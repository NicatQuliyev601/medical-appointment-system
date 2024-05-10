package az.nicat.hospitalsystem.dto.request;

import az.nicat.hospitalsystem.entity.enums.UserAuthority;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SignUpPatientRequest {

    String firstName;
    String lastName;
    String email;
    String username;
    String contactNumber;
    String medicalHistory;

    String password;

}
