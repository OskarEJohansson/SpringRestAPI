package dev.oskarjohansson.projektarbetev2.service;

import com.mongodb.DuplicateKeyException;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RepositoryService {

    Optional<MyUser> getUserByUsername(String username) throws UsernameNotFoundException;

}
