package com.example.StudentRegistrationregistration1.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionErrorManager> resourceNotFound(ResourceNotFoundException ne) {
        ExceptionErrorManager errorResponse = new ExceptionErrorManager();
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationAlreadyExist.class)
    public ResponseEntity<ExceptionErrorManager> handleRegAlreadyExist(RegistrationAlreadyExist ne) {
        ExceptionErrorManager errorResponse = new ExceptionErrorManager();
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setStatus(HttpStatus.FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ExceptionErrorManager> handlePasswordMismatch(PasswordMismatchException ne) {
        ExceptionErrorManager errorResponse = new ExceptionErrorManager();
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(EmailOrUsernameAlreadyExistException.class)
    public ResponseEntity<ExceptionErrorManager> handleEmailOrUsernameExist(EmailOrUsernameAlreadyExistException ne) {
        ExceptionErrorManager errorResponse = new ExceptionErrorManager();
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setStatus(HttpStatus.FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
    }

    @ExceptionHandler(TokenInvalid.class)
    public ResponseEntity<ExceptionErrorManager> handleTokenInvalid(TokenInvalid ne) {
        ExceptionErrorManager errorResponse = new ExceptionErrorManager();
        errorResponse.setMessage(ne.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
