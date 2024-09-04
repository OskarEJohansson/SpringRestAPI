package dev.oskarjohansson.projektarbetev2.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import dev.oskarjohansson.projektarbetev2.exception.customExceptions.RetrievePublicRSAKeyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Qualifier(value = "rsaPublicKey")
    public RSAPublicKey getPublicKeyFromTokenService() throws JOSEException, ParseException {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String jwkUrl = "http://localhost:8081/public-key-controller/v1/public-key";
            String jwkResponse = restTemplate.getForObject(jwkUrl, String.class);
            JWK jwk = JWK.parse(jwkResponse);

            return jwk.toRSAKey().toRSAPublicKey();
        } catch (ResourceAccessException exception) {
            if (exception.getCause() instanceof ConnectException) {

                throw new RetrievePublicRSAKeyException("Failed to connect to resource to retrieve RSA public key", exception);
            }
            throw new RetrievePublicRSAKeyException("Failed to retrieve RSA public key from token service", exception.getCause());

        }

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication-controller/v1/login").permitAll()
                        .requestMatchers("/admin-controller/v1/save-user").permitAll()
                        .anyRequest().authenticated())
                .build();
    }


    @Bean
    JwtDecoder jwtDecoder(@Qualifier(value = "rsaPublicKey") RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

}
