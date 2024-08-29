package dev.oskarjohansson.projektarbetev2.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document
public record UserConsent(Boolean isTermsAndAgreementsConsented, Instant timestamp, String username) {

}
