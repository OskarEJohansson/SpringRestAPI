package dev.oskarjohansson.projektarbetev2.model;

import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @NotBlank(message = "Author name must be provided") String author,
        @NotBlank(message = "Title must be provided") String title,
        @NotBlank(message = "Rating must be provided") int rating,
        @NotBlank(message = "Review can not be empty") String review
) {
}
