package dev.oskarjohansson.projektarbetev2.repository;

import dev.oskarjohansson.projektarbetev2.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
}
