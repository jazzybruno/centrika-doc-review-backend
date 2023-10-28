package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EGender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private EGender gender;
    private String registrationCode;
    private String password;
}
