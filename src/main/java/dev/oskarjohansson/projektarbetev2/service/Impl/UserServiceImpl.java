package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.configuration.SecurityConfiguration;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.repository.UserRepository;
import dev.oskarjohansson.projektarbetev2.service.ConsentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
public final class UserServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final ConsentService consentService;

    public UserServiceImpl(UserRepository userRepository, SecurityConfiguration securityConfiguration, ConsentService consentService) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        this.consentService = consentService;

    }

    public MyUser saveUser(RegisterRequest request) throws Exception {

        return userRepository.save(
                createNewUser(request)
        );
    }

    public List<MyUser> getUsers() {
        return userRepository.findAll();
    }

    public MyUser createNewUser(@Validated RegisterRequest registerRequest) throws IllegalArgumentException {
        return new MyUser(null,
                registerRequest.username(),
                securityConfiguration.passwordEncoder().encode(registerRequest.password()),
                registerRequest.role(),
                consentService.consentToTermsAndAgreement(registerRequest)
        );
    }



}
