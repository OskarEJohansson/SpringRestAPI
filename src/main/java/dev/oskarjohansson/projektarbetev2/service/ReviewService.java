package dev.oskarjohansson.projektarbetev2.service;

import dev.oskarjohansson.projektarbetev2.model.Review;
import dev.oskarjohansson.projektarbetev2.model.ReviewRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService
{
    Review saveReview(ReviewRequest reviewRequest, Authentication authentication) throws IllegalArgumentException;
    void deleteReview() throws IllegalArgumentException;
    Review updateReview() throws IllegalArgumentException;

}
