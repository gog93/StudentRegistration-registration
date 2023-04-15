package com.example.StudentRegistrationregistration1.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewController {


    @GetMapping("/welcome")
    public String welcome(){
        return "This page is protected";
    }
}
