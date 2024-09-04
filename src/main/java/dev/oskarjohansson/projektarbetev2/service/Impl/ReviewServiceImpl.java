package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.Review;
import dev.oskarjohansson.projektarbetev2.model.ReviewRequest;
import dev.oskarjohansson.projektarbetev2.repository.ReviewRepository;
import dev.oskarjohansson.projektarbetev2.repository.UserRepository;
import dev.oskarjohansson.projektarbetev2.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }



    /// CLEAN UP
    @Override
    public Review saveReview(ReviewRequest reviewRequest, Authentication authentication) throws IllegalArgumentException {

        String userID = null;
        Optional<MyUser> response = userRepository.findByUsername(authentication.getName());

        if (response.isPresent()) {
            userID = response.get().id();
        }

        Review review = new Review(null, userID, reviewRequest.author(), reviewRequest.title(), reviewRequest.rating(), reviewRequest.review(), Instant.now());

        return reviewRepository.save(review);

    }

    @Override
    public void deleteReview() throws IllegalArgumentException {

    }

    @Override
    public Review updateReview() throws IllegalArgumentException {
        return null;
    }
}
