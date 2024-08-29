package dev.oskarjohansson.projektarbetev2.repository;


import dev.oskarjohansson.projektarbetev2.model.MyUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<MyUser, String> {

    Optional<MyUser> findByUsername(String username);

}
