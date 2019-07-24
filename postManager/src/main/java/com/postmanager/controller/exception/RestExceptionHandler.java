package com.postmanager.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<String> handleUserValidationFailedException(Exception ex) {
		log.debug("handling AuthenticationException...");
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> invalidJwtAuthentication(Exception ex) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlesBadCredendialsException(Exception ex) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ObjectNotExistException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlesObjectNotFoundException(Exception ex) {
        log.debug("handling ObjectNotExistException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUnauthorizedAccessException(Exception ex) {
        log.debug("handling UnauthorizedAccessException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlUserNameAlreadyExistsException(Exception ex) {
        log.debug("handling UserNameAlreadyExistsException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
