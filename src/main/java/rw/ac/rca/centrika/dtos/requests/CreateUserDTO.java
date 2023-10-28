package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String username;
    private String phoneNumber;
    private String email;
    private String gender;
    private String registrationCode;
    private String password;
    private UUID departmentId;

}
