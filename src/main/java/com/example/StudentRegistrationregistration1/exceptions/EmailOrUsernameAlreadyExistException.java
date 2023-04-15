package com.example.StudentRegistrationregistration1.exceptions;

public class EmailOrUsernameAlreadyExistException extends RuntimeException{

    public EmailOrUsernameAlreadyExistException(String message) {
        super(message);
    }
}
