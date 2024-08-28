package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.configuration.SecurityConfiguration;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.service.MyUserDetailService;
import dev.oskarjohansson.projektarbetev2.service.RepositoryService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public final class MyUserDetailServiceImpl extends MyUserDetailServiceUtility implements MyUserDetailService {

 private final RepositoryService repositoryService;

    public MyUserDetailServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws IllegalArgumentException {
        Optional<MyUser> user = repositoryService.getUserByUsername(username);
        return createUserDetailsAndGrantAuthority(user);
    }

    public MyUser saveUser(RegisterRequest request) throws Exception {
        LOG.info("New user saved with email address: {}", request.username());
        return repositoryService
                .saveUser(
                        createNewUser(request)
                );
    }

    public List<MyUser> getUsers() {
        return repositoryService.getAllUsers();
    }


}
