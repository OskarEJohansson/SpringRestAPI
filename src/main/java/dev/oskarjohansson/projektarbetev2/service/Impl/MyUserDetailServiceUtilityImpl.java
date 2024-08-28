package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.service.UserHandling;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public abstract class MyUserDetailServiceUtilityImpl implements ConsentService, UserHandling {

    private final static Logger LOG = LoggerFactory.getLogger(dev.oskarjohansson.projektarbetev2.service.Impl.MyUserDetailServiceUtilityImpl.class);
    private final SecurityConfiguration securityConfiguration;

    protected MyUserDetailServiceUtilityImpl(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public UserConsent consentToTermsAndAgreement(RegisterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if (request.consent() != null) {
            UserConsent userConsent = new UserConsent(request.consent(), Instant.now());
            userConsentLogger(userConsent, request.username());
            return userConsent;

        } else throw new IllegalArgumentException("Consent must be given");
    }

    private void userConsentLogger(UserConsent userConsent, String userName) {
        if (userConsent.isTermsAndAgreementsConsented() != null) {
            LOG.debug("""
                    User id: {}
                    User consent: {},
                    timestamp: {}
                    """, userName, userConsent, userConsent.timestamp());

        } else LOG.warn("UserConsent object is null for user id:", userName);
    }
public class MyUserDetailServiceUtility implements UserHandling {

    @Override
    public UserDetails createUserDetailsAndGrantAuthority(Optional<MyUser> user) {
        var userObj = user.get();
        return User.builder()
                .username(userObj.username())
                .password(userObj.password())
                .authorities(userObj.role().name())
                .build();
    }

    @Override
    public MyUser createNewUser(RegisterRequest request) throws UsernameNotFoundException {
        return new MyUser(null,
                request.username(),
                securityConfiguration.passwordEncoder().encode(request.password()),
                null,
                consentToTermsAndAgreement(request)
        );
    }
}
