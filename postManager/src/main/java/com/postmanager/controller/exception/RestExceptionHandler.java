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
	
    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    @ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlesBadCredendialsException(Exception ex, WebRequest request) {
        log.debug("handling InvalidJwtAuthenticationException...");
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
