package rw.ac.rca.centrika.utils;

import lombok.Data;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.security.UserPrincipal;

@Data
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private User user;

    public JWTAuthenticationResponse(String accessToken , User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
