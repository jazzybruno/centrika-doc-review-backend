package rw.ac.rca.centrika.dtos.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.models.Role;
import rw.ac.rca.centrika.models.User;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponseDTO {
    private User user;
    private Set<Role> roles;

    public ProfileResponseDTO(User user, Set<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

}

