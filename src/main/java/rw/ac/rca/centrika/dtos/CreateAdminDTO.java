package rw.ac.rca.centrika.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private String gender;
    private String registrationCode;
    private String password;
}
