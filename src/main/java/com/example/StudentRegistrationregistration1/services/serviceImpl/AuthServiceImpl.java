//package com.example.StudentRegistrationregistration1.services.serviceImpl;
//
//
//import com.example.StudentRegistrationregistration1.config.securityConfig.JwtService;
//import com.example.StudentRegistrationregistration1.pojos.AuthRequest;
//import com.example.StudentRegistrationregistration1.services.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    public String authenticate(AuthRequest request){
//        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//        if(!authentication.isAuthenticated()){
//            throw new RuntimeException();
//        }
//        return jwtService.generateToken(request.getUsername());
//
//    }
//
//}
