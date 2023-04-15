package com.example.StudentRegistrationregistration1.controllers;


import com.example.StudentRegistrationregistration1.config.VerifyRecaptcha;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart1;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart2;
import com.example.StudentRegistrationregistration1.pojos.RegistrationRequestPart3;
import com.example.StudentRegistrationregistration1.services.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/verification")
    public String verification(@RequestParam(name = "token") String regId, Model model) {
        model.addAttribute("token", regId);
        model.addAttribute("step2", new RegistrationRequestPart2());

        return "verification";
    }

    @GetMapping("/step1")
    public String firstStep(Model model) {
        model.addAttribute("step1", new RegistrationRequestPart1());
        return "step1";
    }

    @GetMapping("/step2")
    public String firstStep2(@RequestParam(name = "token") String regId, Model model) {
        model.addAttribute("token", regId);
        model.addAttribute("step2", new RegistrationRequestPart2());

        return "step2";
    }

    @GetMapping("/step3")
    public String lastStep(@RequestParam(name = "token") String regId, Model model) {
        model.addAttribute("step3", new RegistrationRequestPart3());
        model.addAttribute("token", regId);

        return "step";
    }

    @PostMapping("/step1")
    public String firstStep(@Valid RegistrationRequestPart1 request, BindingResult result,
                            HttpServletRequest request1, Model model) {
        String gRecaptchaResponse = request1.getParameter("g-recaptcha-response");
        try {
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (verify) {
               if(registrationService.emailOrUsernameExist(request) || !request.getPassword().equalsIgnoreCase(request.getConfirmPassword()) ){
                if (registrationService.emailOrUsernameExist(request)) {
                    model.addAttribute("email", "Email or Username Already in use");
                    model.addAttribute("step1", request);
                }
                if (!request.getPassword().equalsIgnoreCase(request.getConfirmPassword())) {
                    model.addAttribute("password", "Password does not match");
                    model.addAttribute("step1", request);
                }
                   return "step1";

               }
                registrationService.registrationPart1(request);
                return "confirm";
            } else {
                model.addAttribute("captcha", "Invalid captcha");
                model.addAttribute("step1", request);
                return "step1";
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return "step1";

        }
    }
    @PostMapping("/step2")
    public String secondStep(@RequestParam(name = "token") String regId, RegistrationRequestPart2 request, Model model) {
        if(request.getData_sharing().equals("Other")){
          model.addAttribute("token",regId);
            return "step2Issue";
        }
        registrationService.registrationPart2(regId, request);
        return "redirect:/api/registration/step3?token=" + regId;
    }

    @PostMapping("/step3")
    public String lastStep(@RequestParam(name = "token") String regId, RegistrationRequestPart3 request,
                           @RequestParam("upload") MultipartFile multipartFile) throws IOException {
        registrationService.registrationPart3(regId, request, multipartFile);
        return "redirect:/api/registration/step1";
    }

    @GetMapping("/verify_email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "token") String token) {
        return ResponseEntity.ok(registrationService.verifyRegToken(token));
    }

}