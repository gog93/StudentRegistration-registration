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
@Table(name = "Registration1_details")
public class Registration1 {
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

    private String firstName;
    private String surname;
    private String department;
    private String institution;
    private String city;
    private String country;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    private boolean termsAndConditions;
    @Column(name = "reg_id", unique = true)
    private String registrationId;

    @PrePersist
    public void createdAt(){created_At = new Date();}

    @PreUpdate
    public void updatedAt(){updated_At = new Date();}
}
