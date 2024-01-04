package rw.ac.rca.centrika.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.LoginResponse;
import rw.ac.rca.centrika.dtos.requests.ResetPasswordDTO;
import rw.ac.rca.centrika.dtos.requests.SignInDTO;
import rw.ac.rca.centrika.dtos.responses.ProfileResponseDTO;
import rw.ac.rca.centrika.enumerations.EStatus;
import rw.ac.rca.centrika.exceptions.BadRequestAlertException;
import rw.ac.rca.centrika.exceptions.ResourceNotFoundException;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IUserRepository;
import rw.ac.rca.centrika.services.AuthenticationService;
import rw.ac.rca.centrika.services.MailService;
import rw.ac.rca.centrika.services.UserService;
import rw.ac.rca.centrika.utils.ExceptionUtils;
import rw.ac.rca.centrika.utils.HashUtil;
import rw.ac.rca.centrika.utils.Utility;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final IUserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, IUserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public LoginResponse login(SignInDTO dto) {
        return null;
    }

    @Override
    @Transactional
    public boolean verifyAccount(String email, String code) throws ResourceNotFoundException {
        try {
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            if(user.getActivationCode().equals(code)){
                user.setStatus(EStatus.ACTIVE);
                userRepository.save(user);
                mailService.sendWelcomeEmail(user);
                return true;
            }else{
                throw new BadRequestAlertException("Incorrect verification code");
            }
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return false;
        }
    }

    @Override
    public boolean verifyResetCode(String email, String code) throws ResourceNotFoundException {
        try {
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            if(user.getActivationCode().equals(code)){
                user.setStatus(EStatus.ACTIVE);
                userRepository.save(user);
                return true;
            }else{
                throw new BadRequestAlertException("Incorrect verification code");
            }
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return false;
        }
    }

    @Override
    public User resendVerificationCode(String email) throws ResourceNotFoundException {
        try {
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            mailService.sendAccountVerificationEmail(user);
            return user;
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    @Transactional
    public User resetPassword(ResetPasswordDTO dto) throws ResourceNotFoundException {
        try {
            User user = userRepository.findUserByEmail(dto.getEmail()).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            user.setPassword(HashUtil.hashPassword(dto.getNewPassword()));
            return user;
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    @Transactional
    public User initiatePasswordReset(String email) throws ResourceNotFoundException {
        try {
            User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestAlertException("User with provided email not found"));
            user.setActivationCode(Utility.randomUUID(6, 0, 'N'));
            mailService.sendResetPasswordMail(user);
            return user;
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public ProfileResponseDTO getUserProfile() throws ResourceNotFoundException {
        try {
            User user = this.userService.getLoggedInUser();

                return new ProfileResponseDTO(user , user.getRoles());
        }catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
}
