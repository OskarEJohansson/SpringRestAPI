package dev.oskarjohansson.projektarbetev2.controller;

import com.mongodb.DuplicateKeyException;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.service.MyUserDetailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
public class RegisterController {

    private final MyUserDetailService myUserDetailService;

    public RegisterController(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterRequest request) throws Exception {
            MyUser response = myUserDetailService.saveUser(request);
            return ResponseEntity.ok(response);
    }
}