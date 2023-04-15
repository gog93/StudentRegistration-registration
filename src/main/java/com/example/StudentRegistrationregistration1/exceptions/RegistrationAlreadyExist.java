package com.example.StudentRegistrationregistration1.exceptions;

public class RegistrationAlreadyExist extends RuntimeException{

    public RegistrationAlreadyExist(String message) {
        super(message);
    }
}
