package com.example.StudentRegistrationregistration1.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@ToString
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @JsonIgnore
    @Column(name="createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_At;

    @JsonIgnore
    @Column(name="updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_At;

    @Column(name = "firstName", nullable = false)
    private String first_name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "roles", nullable = false)
    private String Roles;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "user_id", unique = true, nullable = false)
    private String user_id;
    @Column(name = "institution", nullable = false)
    private String institution;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "department", nullable = false)
    private String department;
    private Integer email_verification_token;
    private String activation_code;
    private String date_of_approval;
    private String data_sharing;
    private String data_sharing_2;
    private String sample_sharing;
    private String sample_sharing_2;
    private String monogenic_data1;
    private String duration_of_storage;
    private String date_of_expire;
    private byte[] file;
    private int number_of_probands;
    private String recaptcha;
    private Boolean is_active = false;
    private Boolean termsAndConditions;
    private Boolean is_email_verified = false;
    private Boolean is_notified_for_check_list = false;
    private Boolean is_notified_for_file_upload = false;

    @PrePersist
    public void createdAt(){created_At = new Date();}

    @PreUpdate
    public void updatedAt(){updated_At = new Date();}

}
