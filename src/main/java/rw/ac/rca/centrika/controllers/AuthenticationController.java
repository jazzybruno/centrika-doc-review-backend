package rw.ac.rca.centrika.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import rw.ac.rca.centrika.utils.ApiResponse;
import rw.ac.rca.centrika.utils.HashUtil;
import rw.ac.rca.centrika.utils.JWTAuthenticationResponse;
import rw.ac.rca.centrika.utils.TokenUtility;

@RestController
@RequestMapping (path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;
    private final EmailService emailService;
//    @Value("${token.expirationTimeMillis}")
    private final long tokenExpirationTimeMillis = 3600000;
    private static final String BASE_URL = "https://example.com/reset-password";
    @Autowired
    public AuthenticationController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager , UserServiceImpl userService , EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.emailService = emailService;
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
                  return ResponseEntity.ok(ApiResponse.success("Password reset initiated successfully. Check your email for instructions."));
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
                    return ResponseEntity.ok(ApiResponse.success("Email verified successfully."));
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
                    return ResponseEntity.ok(ApiResponse.success("Password reset successfully."));
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
