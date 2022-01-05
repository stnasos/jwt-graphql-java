package me.nos.jwtgraphqljava.errors;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Log4j2
//@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<Object> handleJwtCreationException(JWTCreationException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    //@ExceptionHandler(value = {JWTVerificationException.class})
    public ResponseEntity<Object> handleJwtVerificationException(JWTVerificationException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), UNAUTHORIZED);
    }

    //@ExceptionHandler(value = {JWTDecodeException.class})
    public ResponseEntity<Object> handleJwtDecodeException(JWTDecodeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), UNAUTHORIZED);
    }

    //@ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), UNAUTHORIZED);
    }

    //@ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), NOT_FOUND);
    }

    //@ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }
}
