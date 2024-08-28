package dev.oskarjohansson.projektarbetev2.service.Impl;

import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MyUserDetailServiceImplTest {

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void saveUserAndCheckPasswordEncodingSuccessful() throws Exception {

        RegisterRequest request = new RegisterRequest.Builder()
                .userName("use")
                .password("123456")
                .role(null)
                .consent(true)
                .build();

        MyUser user = myUserDetailService.saveUser(request);
        Assertions.assertNotEquals(user.password(),request.password());
    }

    @Test
    void saveUserAndCheckConsentSaveAndTimeStamp() throws Exception {
        RegisterRequest request = new RegisterRequest.Builder()
                .userName("user")
                .password("123456")
                .role(null)
                .consent(true)
                .build();

        MyUser user = myUserDetailService.saveUser(request);
        Assertions.assertEquals(user.userConsent().isTermsAndAgreementsConsented(), true);
        Assertions.assertNotNull(user.userConsent().timestamp());

    }

    @Test
    void saveUserAndGetDuplicateKeyException() throws Exception {

        RegisterRequest request1 = new RegisterRequest.Builder()
                .userName("oskar")
                .password("123456")
                .role(null)
                .consent(true)
                .build();


        Exception exception = Assertions.assertThrows(DuplicateKeyException.class, () -> {
            myUserDetailService.saveUser(request1);
        });
        System.out.println("SOUT ->>>>>>>> " + exception.getMessage());
        Assertions.assertTrue(exception.getMessage().contains("duplicate key error"));
    }


}