package dev.oskarjohansson.projektarbetev2.service;

import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService  {

    Optional<MyUser> loadUserByUsername(String username) throws UsernameNotFoundException;

    Optional<MyUser> saveUser(RegisterRequest registerRequest) throws IllegalArgumentException;

    MyUser createNewUser(RegisterRequest registerRequest) throws IllegalArgumentException;

}
