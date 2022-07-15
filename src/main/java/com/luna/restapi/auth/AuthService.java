package com.luna.restapi.auth;

import com.luna.restapi.exceptions.NotFoundException;
import com.luna.restapi.mail.MailService;
import com.luna.restapi.users.User;
import com.luna.restapi.users.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
@ApplicationScoped
public class AuthService {

    private UserRepository userRepository;
    private MailService mailService;

    @Inject
    public AuthService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void signup(SignupRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setPassword(request.getPassword());
        user.setValidationCode(generateCode());
        try {
            userRepository.persist(user);
            sendEmailToVerify(user.getEmail(), user.getValidationCode());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void sendEmailToVerify(String email, String code) {
        var msg = "Click link to activate account: http://localhost:8080/auth/validate/"
                .concat(email).concat("/").concat(code);
        mailService.sendEmail(email, "Account Validation - Quarkus Test", msg);
        log.info("Mail sent to: {}", email);
    }

    private String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public UserResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + request.getEmail()));

        if (!user.isVerified()) return null;

        if (user.getPassword().contentEquals(request.getPassword())) {
            var response = new UserResponse();
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setLastname(user.getLastname());
            return response;
        }

        return null;
    }

    @Transactional
    public boolean validateAccount(String email, String code) {
        var found = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));

        if (found.getValidationCode().contentEquals(code)) {
            found.setVerified(true);
            userRepository.persist(found);
            return true;
        }
        return false;
    }

}
