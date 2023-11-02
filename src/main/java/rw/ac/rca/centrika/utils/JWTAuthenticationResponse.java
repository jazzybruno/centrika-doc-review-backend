package rw.ac.rca.centrika.utils;

import lombok.Data;
import rw.ac.rca.centrika.security.UserPrincipal;

@Data
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserPrincipal userPrincipal;

    public JWTAuthenticationResponse(String accessToken , UserPrincipal userPrincipal) {
        this.accessToken = accessToken;
        this.userPrincipal = userPrincipal;
    }
}
