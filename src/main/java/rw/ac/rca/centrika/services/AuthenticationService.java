package rw.ac.rca.centrika.services;


import rw.ac.rca.centrika.dtos.requests.SignInDTO;
import rw.ac.rca.centrika.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.centrika.exceptions.ResourceNotFoundException;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.centrika.dtos.requests.LoginResponse;
import rw.ac.rca.centrika.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.centrika.models.User;

import java.util.UUID;

public interface AuthenticationService {
    public LoginResponse login (SignInDTO dto);
    public boolean verifyAccount(String email, String code) throws ResourceNotFoundException;
    public boolean verifyResetCode(String email, String code) throws ResourceNotFoundException;
    public User resendVerificationCode(String email) throws ResourceNotFoundException;
    public User resetPassword(ResetPasswordDTO dto) throws ResourceNotFoundException;
    public User initiatePasswordReset(String email) throws ResourceNotFoundException;
    // other methods
    public ProfileResponseDTO getUserProfile() throws ResourceNotFoundException;

}
