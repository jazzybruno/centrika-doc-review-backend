package rw.ac.rca.centrika.utils;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
