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
@Table(name = "Registration3_details")
public class Registration3 {

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

    private String registrationId;
    private byte[] file;
    private int number_of_probands;
    private String recaptcha;


    @PrePersist
    public void createdAt(){created_At = new Date();}

    @PreUpdate
    public void updatedAt(){updated_At = new Date();}
}
