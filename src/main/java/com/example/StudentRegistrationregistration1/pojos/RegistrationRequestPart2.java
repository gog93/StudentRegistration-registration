package com.example.StudentRegistrationregistration1.pojos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegistrationRequestPart2 {

    private String date_of_approval;
    private String data_sharing;
    private String data_sharing_2;
    private String sample_sharing;
    private String sample_sharing_2;
    private String monogenic_data1;
    private String duration_of_storage;
    private String date_of_expire;

}
