package rw.ac.rca.centrika.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.SignInDTO;
import rw.ac.rca.centrika.security.JwtTokenProvider;
import rw.ac.rca.centrika.utils.ApiResponse;
import rw.ac.rca.centrika.utils.JWTAuthenticationResponse;

@RestController
@RequestMapping (path = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping (path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody SignInDTO signInDTO) {
        String jwt = null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ApiResponse.success(new JWTAuthenticationResponse(jwt)));
    }

    @PostMapping("/initiate-password-reset")
    public ResponseEntity<ApiResponse> initiatePasswordReset(@RequestParam String email) {
        // Check if the email exists in your user database
        // Generate a unique token for password reset
        // Save the token in the database along with the user's email and an expiration timestamp
        // Send an email to the user with the reset link (containing the token)
        // Return a success response
        return ResponseEntity.ok(ApiResponse.success("Password reset initiated successfully. Check your email for instructions."));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String token) {
        // Check if the token is valid and not expired
        // Mark the user's email as verified in the database
        // Delete or invalidate the token to prevent further use
        // Return a success response
        return ResponseEntity.ok(ApiResponse.success("Email verified successfully."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // Check if the token is valid and not expired
        // Update the user's password in the database
        // Delete or invalidate the token to prevent further use
        // Return a success response
        return ResponseEntity.ok(ApiResponse.success("Password reset successful."));
    }
}
