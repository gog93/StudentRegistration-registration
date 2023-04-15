package com.example.StudentRegistrationregistration1.services;

import com.example.StudentRegistrationregistration1.Entities.Registration1;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart1;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart2;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart3;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface RegistrationService {
    @Transactional
    String registrationPart1(RegistrationRequestPart1 request);

    String registrationPart2(String registrationId, RegistrationRequestPart2 request);

    @Transactional
    String registrationPart3(String registrationId, RegistrationRequestPart3 request, MultipartFile multipartFile) throws IOException;

    String verifyRegToken(String token);
    Registration1 findByEmail(String email);
    Boolean emailOrUsernameExist(RegistrationRequestPart1 request);
}
