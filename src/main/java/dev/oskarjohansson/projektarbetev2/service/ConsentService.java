package dev.oskarjohansson.projektarbetev2.service;

import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.model.UserConsent;

public interface ConsentService {

    UserConsent consentToTermsAndAgreement(RegisterRequest request);

    void userConsentLogger(UserConsent userConsent, String userName);
}
