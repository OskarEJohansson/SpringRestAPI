package dev.oskarjohansson.projektarbetev2.service;

import org.springframework.security.core.Authentication;



public interface TokenService {

    String generateToken(Authentication authentication);

}
