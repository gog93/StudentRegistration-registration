package com.example.StudentRegistrationregistration1.dao;

import com.example.StudentRegistrationregistration1.Entities.Registration1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Registration1Repository extends JpaRepository<Registration1, Long> {

    Boolean existsByRegistrationId(String registrationId);
    Registration1 findByRegistrationId(String regId);
    Registration1 findByEmail(String email);
    Boolean existsByEmailOrUsername(String email, String username);


}
