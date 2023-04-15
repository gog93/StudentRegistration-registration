package com.example.StudentRegistrationregistration1.dao;


import com.example.StudentRegistrationregistration1.Entities.Registration3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Registration3Repository extends JpaRepository<Registration3, Long> {

    Registration3 findByRegistrationId(String registrationId);

}
