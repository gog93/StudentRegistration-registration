package com.example.StudentRegistrationregistration1.pojos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDetails {


    private String first_name;
    private String surname;
    private String institution;
    private String city;
    private String country;
    private String department;
    private String email;
    private String password;
    private String date_of_approval;
    private String data_sharing;
    private String data_sharing_2;
    private String sample_sharing;
    private String sample_sharing_2;
    private String monogenic_data1;
    private String duration_of_storage;
    private String date_of_expire;
    private String username;
    private byte[] file;
    private int number_of_probands;
    private String recaptcha;



}
