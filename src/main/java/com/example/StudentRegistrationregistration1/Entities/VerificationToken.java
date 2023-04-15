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
@Table(name = "verification_token")
public class VerificationToken {

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

    private String email;
    private String token;
    private Boolean isUsed = false;


    @PrePersist
    public void createdAt(){created_At = new Date();}

    @PreUpdate
    public void updatedAt(){updated_At = new Date();}
}
