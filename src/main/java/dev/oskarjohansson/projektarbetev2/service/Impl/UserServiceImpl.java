package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.configuration.SecurityConfiguration;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.repository.UserRepository;
import dev.oskarjohansson.projektarbetev2.service.ConsentService;
import dev.oskarjohansson.projektarbetev2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
public final class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;
    private final ConsentService consentService;

    public UserServiceImpl(UserRepository userRepository, SecurityConfiguration securityConfiguration, ConsentService consentService) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        this.consentService = consentService;

    }

    public Optional<MyUser> saveUser(RegisterRequest registerRequest) throws IllegalArgumentException {
        return Optional.of(userRepository.save(createNewUser(registerRequest)));
    }

    public List<MyUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<MyUser> loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
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
