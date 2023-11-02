package rw.ac.rca.centrika.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.centrika.dtos.requests.SignInDTO;
import rw.ac.rca.centrika.dtos.requests.VerifyEmailDTO;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.security.JwtTokenProvider;
import rw.ac.rca.centrika.services.serviceImpl.EmailService;
import rw.ac.rca.centrika.services.serviceImpl.UserServiceImpl;
import rw.ac.rca.centrika.utils.*;

@RestController
@RequestMapping (path = "/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final EmailService emailService;
//    @Value("${token.expirationTimeMillis}")
    private final long tokenExpirationTimeMillis = 3600000;
    private final AuthenticationProvider authenticationProvider;
    private static final String BASE_URL = "https://example.com/reset-password";
    @Autowired
    public AuthenticationController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager , UserServiceImpl userService , EmailService emailService , AuthenticationProvider authenticationProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.emailService = emailService;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping (path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody SignInDTO signInDTO) {
        String jwt = null;
        System.out.println("I am in the authentication api");
        // Create a UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());

// Set the authentication in the SecurityContext
        Authentication authentication = authenticationProvider.authenticate(authRequest);
        System.out.println("I am in the authentication api after the authentication manager");
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("I am in the authentication api after the authentication manager third part");
            jwt = jwtTokenProvider.generateToken(authentication);
            System.out.println("I am in the authentication api after the authentication manager fourth part");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully Logged in",
                new JWTAuthenticationResponse(jwt)
        ));
    }

    @PostMapping("/initiate-password-reset")
    @Transactional
    public ResponseEntity<ApiResponse> initiatePasswordReset(@RequestParam String email) {
        // Check if the email exists in your user database
         User user = userService.getUserByEmail(email);
         if(user != null){
             String token = TokenUtility.generateToken();
             user.setToken(token);
             // Generate a unique token for password reset
             String passwordResetLink = generatePasswordResetLink(user.getEmail() , user.getToken());
              try {
                 emailService.sendPasswordResetEmail(user , passwordResetLink);
                  return ResponseEntity.ok().body(new ApiResponse(
                          true ,
                          "Initiate password reset was a success"
                  ));
              }catch (Exception e){
                  return ResponseEntity.internalServerError().body(new ApiResponse(
                          false,
                          e.getMessage()
                  ));
              }
         }else{
             return ResponseEntity.badRequest().body(new ApiResponse(
                     false ,
                     "The account is not present"
             ));
         }
    }

    @GetMapping("/verify-email")
    @Transactional
    public ResponseEntity<ApiResponse> verifyEmail(@RequestBody VerifyEmailDTO verifyEmailDTO) {
        User user = userService.getUserByEmail(verifyEmailDTO.getEmail());
        if(user == null){
            if(TokenUtility.isTokenValid(verifyEmailDTO.getCode() , tokenExpirationTimeMillis)){
                if(user.getToken().equals(verifyEmailDTO.getCode())){
                    user.setToken(null);
                    user.setVerified(true);
                    return ResponseEntity.ok().body(new ApiResponse(
                            true ,
                            "Email verified successfully."
                    ));
                }else{
                    return ResponseEntity.badRequest().body(new ApiResponse(
                            false ,
                            "The token is incorrect"
                    ));
                }
            }else{
                return ResponseEntity.badRequest().body(new ApiResponse(
                        false ,
                        "The token is invalid"
                ));
            }
        }else{
            return ResponseEntity.badRequest().body(new ApiResponse(
                    false ,
                    "The account is not present"
            ));
        }
    }


    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        User user = userService.getUserByEmail(resetPasswordDTO.getEmail());
        if(user == null){
            if(TokenUtility.isTokenValid(resetPasswordDTO.getCode() , tokenExpirationTimeMillis)){
                if(user.getToken().equals(resetPasswordDTO.getCode())){
                    user.setToken(null);
                     String hashedPassword = HashUtil.hashPassword(resetPasswordDTO.getNewPassword());
                     user.setPassword(hashedPassword);
                    return ResponseEntity.badRequest().body(new ApiResponse(
                            false ,
                            "Password reset successfully"
                    ));
                }else{
                    return ResponseEntity.badRequest().body(new ApiResponse(
                            false ,
                            "The token is incorrect"
                    ));
                }
            }else{
                return ResponseEntity.badRequest().body(new ApiResponse(
                        false ,
                        "The token is invalid"
                ));
            }
        }else{
            return ResponseEntity.badRequest().body(new ApiResponse(
                    false ,
                    "The account is not present"
            ));
        }
    }

    public static String generatePasswordResetLink(String email, String token) {
        // Create the password reset link by appending the email and token to the base URL
        return BASE_URL + "?email=" + email + "&token=" + token;
    }
}
