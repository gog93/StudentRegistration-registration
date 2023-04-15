package com.example.StudentRegistrationregistration1.exceptions;

public class PasswordMismatchException extends RuntimeException{

    public PasswordMismatchException(String message) {
        super(message);
    }
}
