package com.example.gameserver.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e) {
        LOG.debug(e.getMessage());
        var message = e.getMessage().isBlank() ? "Entity doesn't exist" : e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleException(UsernameNotFoundException e) {
        LOG.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UsernameUsedException.class)
    public ResponseEntity<Object> handleException(UsernameUsedException e) {
        LOG.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(RoleNameUsedException.class)
    public ResponseEntity<Object> handleException(RoleNameUsedException e) {
        LOG.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException e) {
        LOG.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
    }

    @ExceptionHandler(GameReviewDuplicateException.class)
    public ResponseEntity<Object> handleException(GameReviewDuplicateException e) {
        LOG.debug(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}