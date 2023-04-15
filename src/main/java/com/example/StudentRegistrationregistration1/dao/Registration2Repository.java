package com.example.StudentRegistrationregistration1.dao;

import com.example.StudentRegistrationregistration1.Entities.Registration2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Registration2Repository extends JpaRepository<Registration2, Long> {

    Registration2 findByRegistrationId(String registrationId);

    Boolean existsByRegistrationId(String registrationId);
}
