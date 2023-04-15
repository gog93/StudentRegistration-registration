package com.example.StudentRegistrationregistration1.pojos;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegistrationRequestPart1 {
    @NotEmpty(message = "Name is required.")

    private String name;
    @NotEmpty(message = "Name is required.")

    private String surname;
    @NotEmpty(message = "Name is required.")

    private String department;
    @NotEmpty(message = "Name is required.")

    private String institution;
    @NotEmpty(message = "Name is required.")

    private String city;
    @NotEmpty(message = "Name is required.")

    private String country;
    @NotEmpty(message = "Name is required.")

    private String email;
    @NotEmpty(message = "Name is required.")

    private String username;
    @NotEmpty(message = "Name is required.")

    private String password;
    @NotEmpty(message = "Name is required.")

    private String confirmPassword;

    private boolean termsAndConditions;
}
