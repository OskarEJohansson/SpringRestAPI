package dev.oskarjohansson.projektarbetev2.exception;

import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class GlobalExceptionHandling {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandling.class);

    @ExceptionHandler
    public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException ex){
        LOG.error("DuplicateKeyException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate key error" + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex){
        LOG.error("DataAccessException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleGeneralException(Exception ex){
        LOG.error("GeneralException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred" + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex){
        LOG.error("AuthenticationException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization error: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex){
        LOG.error("IllegalArgumentException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal argument exception error: " + ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex){
        LOG.error("UsernameNotFoundException error, stack trace: {}", ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username not found: " +  ex.getMessage());
    }
}
