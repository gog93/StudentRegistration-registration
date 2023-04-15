package com.example.StudentRegistrationregistration1.pojos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegistrationRequestPart3 {

    private String file;
    private int number_of_probands;
    private String recaptcha;

}
