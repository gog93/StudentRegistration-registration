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
@Table(name = "Registration2_details")
public class Registration2 {

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
    private String date_of_approval;
    private String data_sharing;
    private String data_sharing_2;
    private String sample_sharing;
    private String sample_sharing_2;
    private String monogenic_data1;
    private String duration_of_storage;
    private String date_of_expire;


    @PrePersist
    public void createdAt(){created_At = new Date();}

    @PreUpdate
    public void updatedAt(){updated_At = new Date();}

}
