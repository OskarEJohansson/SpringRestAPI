package dev.oskarjohansson.projektarbetev2.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Validated
@Document(collection = "review")
public record Review(String id, @NotBlank(message = "User Id must be provided") String userId, @NotBlank(message = "Author name must be provided") String author, @NotBlank(message = "Title must be provided") String title, @NotBlank(message = "Rating must be provided") String rating, @NotBlank(message = "Review can not be empty") String review, Instant createdAt){

}
