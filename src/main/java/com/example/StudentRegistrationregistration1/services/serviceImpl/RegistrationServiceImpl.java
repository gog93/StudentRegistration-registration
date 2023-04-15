package com.example.StudentRegistrationregistration1.services.serviceImpl;


import com.example.StudentRegistrationregistration1.Entities.*;
import com.example.StudentRegistrationregistration1.config.EmailConfig;
import com.example.StudentRegistrationregistration1.dao.*;
import com.example.StudentRegistrationregistration1.exceptions.*;
import com.example.StudentRegistrationregistration1.pojos.*;
import com.example.StudentRegistrationregistration1.services.RegistrationService;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final Registration1Repository registration1Repository;
    private final String upload = System.getProperty("user.home") + "/img/";

    private final EmailConfig emailConfig;

    private final ServletRequest ServerRequest;
    private final Registration2Repository registration2Repository;
    private final Registration3Repository registration3Repository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

//  private final PasswordEncoder encoder;





    @Override
    @Transactional
    public String registrationPart1(RegistrationRequestPart1 request){

        if(emailOrUsernameExist(request)){
            throw new EmailOrUsernameAlreadyExistException("Email or Username Already in use");
        }
        String registrationId = UUID.randomUUID().toString();
        String BASE_URL = "http://" + ServerRequest.getServerName() + ":" + ServerRequest.getServerPort() + "/api/registration/verification";
        Registration1 registration = mapToEntity(registrationId, request);
        SendEmailRequestDto emailRequest = SendEmailRequestDto.builder()
                .subject("Continue Registration")
                .body(BASE_URL + "?token=" + registrationId)
                .receiver(request.getEmail())
                .build();
        emailConfig.sendEmail(emailRequest);
        registration1Repository.save(registration);
        return "Registration saved";
    }


    @Override
    public String registrationPart2(String registrationId, RegistrationRequestPart2 request){

        //check if the step 1 has already been done
        if(!isFirstStepCompleted(registrationId)){
            throw new ResourceNotFoundException("Step 1 has not been completed");
        }else{
            Registration2 registration2 = registration2Repository.findByRegistrationId(registrationId);
            if(registration2 != null){
                throw new RegistrationAlreadyExist("This step has already been completed!, Proceed to the final step");
            }
            registration2 = mapToEntity(registrationId, request);
            registration2Repository.save(registration2);

            return "Information saved, proceed to the final step";
        }
    }



    @Override
    @Transactional
    public String registrationPart3(String registrationId, RegistrationRequestPart3 request, MultipartFile multipartFile) throws IOException {

        //Check if second step is already completed
        if(!isSecondStepCompleted(registrationId)){
            throw new ResourceNotFoundException("Step 2 has not been completed");
        }else{
            Registration3 registration3 = registration3Repository.findByRegistrationId(registrationId);
            if(registration3 != null){
                throw new RegistrationAlreadyExist("Registration has already been completed");
            }
            registration3 = mapToEntity(registrationId, request, multipartFile);
            registration3Repository.save(registration3);

            User user = dtoToUser(userDetails(registrationId));
            user.setUser_id(UUID.randomUUID().toString());
            user.setRoles("USER");
            userRepository.save(user);
            String verification_token = UUID.randomUUID().toString();
            VerificationToken verificationToken = VerificationToken.builder()
                    .token(verification_token)
                    .email(user.getEmail())
                    .isUsed(false)
                    .build();
            verificationTokenRepository.save(verificationToken);
            String BASE_URL = "http://" + ServerRequest.getServerName() + ":" + ServerRequest.getServerPort()
                    + "/api/registration/verify_email";
            SendEmailRequestDto emailRequest = SendEmailRequestDto.builder()
                    .subject("Verify email")
                    .body(BASE_URL + "?token=" + verification_token)
                    .receiver(user.getEmail())
                    .build();
            emailConfig.sendEmail(emailRequest);
        }
        return "Registration completed, Check your email to activate your account!";
    }


    @Override
    public String verifyRegToken(String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if(verificationToken == null || verificationToken.getIsUsed() || isExpired(verificationToken.getCreated_At())){
            throw new TokenInvalid("Token is invalid!");
        }
        User user = userRepository.findByEmail(verificationToken.getEmail()).get();
        user.setIs_email_verified(true);
        user.setIs_active(true);
        userRepository.save(user);
        verificationToken.setIsUsed(true);
        verificationTokenRepository.save(verificationToken);
        return "Email verified!";
    }

    @Override
    public Registration1 findByEmail(String email) {
       return registration1Repository.findByEmail(email);
    }

    protected Registration1 mapToEntity(String registrationId, RegistrationRequestPart1 request){
        return Registration1.builder()
                .registrationId(registrationId)
                .city(request.getCity())
                .email(request.getEmail())
                .country(request.getCountry())
//                .password(encoder.encode(request.getPassword()))
                .password(request.getPassword())
                .department(request.getDepartment())
                .firstName(request.getName())
                .surname(request.getSurname())
                .institution(request.getInstitution())
                .termsAndConditions(request.isTermsAndConditions())
                .username(request.getUsername())
                .build();

    }

    protected Registration2 mapToEntity(String registrationId, RegistrationRequestPart2 request){
        return Registration2.builder()
                .registrationId(registrationId)
                .data_sharing(request.getData_sharing())
                .data_sharing_2(request.getData_sharing_2())
                .date_of_approval(request.getDate_of_approval())
                .date_of_expire(request.getDate_of_expire())
                .duration_of_storage(request.getDuration_of_storage())
                .monogenic_data1(request.getMonogenic_data1())
                .sample_sharing(request.getSample_sharing())
                .sample_sharing_2(request.getSample_sharing_2())
                .build();
    }


    protected Registration3 mapToEntity(String registrationId, RegistrationRequestPart3 request, MultipartFile file) throws IOException {
        return Registration3.builder()
                .registrationId(registrationId)
                .number_of_probands(request.getNumber_of_probands())
                .file(file.getBytes())
                .recaptcha(request.getRecaptcha())
                .build();
    }


    public Boolean isPasswordMatch(RegistrationRequestPart1 request){
        return request.getPassword().equals(request.getConfirmPassword());
    }

    public Boolean emailOrUsernameExist(RegistrationRequestPart1 request){
        return registration1Repository.existsByEmailOrUsername(request.getEmail(), request.getUsername());
    }


    protected Boolean isFirstStepCompleted(String registrationId){
        return registration1Repository.existsByRegistrationId(registrationId);
    }

    protected Boolean isSecondStepCompleted(String registrationId){
        return registration2Repository.existsByRegistrationId(registrationId);
    }

    protected static boolean isExpired(Date tokenTimestamp) {
        Instant instant = tokenTimestamp.toInstant();
        Instant expirationTimestamp = instant.plus(15, ChronoUnit.MINUTES);
        Instant currentTimestamp = Instant.now();
        return currentTimestamp.isAfter(expirationTimestamp);
    }




    protected UserDetails userDetails(String regId){
        Registration1 registration1 = registration1Repository.findByRegistrationId(regId);
        Registration2 registration2 = registration2Repository.findByRegistrationId(regId);
        Registration3 registration3 = registration3Repository.findByRegistrationId(regId);

        return UserDetails.builder()
                .first_name(registration1.getFirstName())
                .surname(registration1.getSurname())
                .username(registration1.getUsername())
                .institution(registration1.getInstitution())
                .city(registration1.getCity())
                .country(registration1.getCountry())
                .department(registration1.getDepartment())
                .email(registration1.getEmail())
                .password(registration1.getPassword())
                .date_of_approval(registration2.getDate_of_approval())
                .data_sharing(registration2.getData_sharing())
                .data_sharing_2(registration2.getData_sharing_2())
                .sample_sharing(registration2.getSample_sharing())
                .sample_sharing_2(registration2.getSample_sharing_2())
                .monogenic_data1(registration2.getMonogenic_data1())
                .duration_of_storage(registration2.getDuration_of_storage())
                .date_of_expire(registration2.getDate_of_expire())
                .file(registration3.getFile())
                .number_of_probands(registration3.getNumber_of_probands())
                .recaptcha(registration3.getRecaptcha())
                .build();

    }



    protected User dtoToUser(UserDetails userDetails){
        return User.builder()
                .first_name(userDetails.getFirst_name())
                .surname(userDetails.getSurname())
                .username(userDetails.getUsername())
                .institution(userDetails.getInstitution())
                .city(userDetails.getCity())
                .country(userDetails.getCountry())
                .department(userDetails.getDepartment())
                .email(userDetails.getEmail())
                .password(userDetails.getPassword())
                .date_of_approval(userDetails.getDate_of_approval())
                .data_sharing(userDetails.getData_sharing())
                .data_sharing_2(userDetails.getData_sharing_2())
                .sample_sharing(userDetails.getSample_sharing())
                .sample_sharing_2(userDetails.getSample_sharing_2())
                .monogenic_data1(userDetails.getMonogenic_data1())
                .duration_of_storage(userDetails.getDuration_of_storage())
                .date_of_expire(userDetails.getDate_of_expire())
                .file(userDetails.getFile())
                .number_of_probands(userDetails.getNumber_of_probands())
                .recaptcha(userDetails.getRecaptcha())
                .build();
    }

    public Registration3 uploadFile(Registration3 registration3,MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileNotFoundException("File or emailId is null");
            }
            String fileName = file.getOriginalFilename();
            byte[] uploadedFile = file.getInputStream().readAllBytes();
            registration3.setFile(uploadedFile);

            return registration3;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
