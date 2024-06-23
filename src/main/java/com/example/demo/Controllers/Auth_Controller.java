package com.example.demo.Controllers;

import com.example.demo.DTO.AuthDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.PersonDTO;
import com.example.demo.DTO.PersonLoginDTO;
import com.example.demo.Entities.Person;
import com.example.demo.Security.JWTUtil;
import com.example.demo.Security.PersonDetails;
import com.example.demo.Service.Person_Details_Service;
import com.example.demo.Service.Person_Service;
import com.example.demo.Service.Registration_Service;
import org.apache.juli.logging.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class Auth_Controller {
    private final PasswordEncoder passwordEncoder;
    private final Registration_Service registrationService;

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final Person_Service personService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthDTO authenticationRequest) throws Exception {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getName(),
                        authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authInputToken.getName());
        Map<String, String> response = new HashMap<>();
        response.put("jwt-token", token);
        response.put("name", authInputToken.getName());
        response.put("email", personService.findbyName(authInputToken.getName()).get().getGmail());
        response.put("id", personService.findbyName(authInputToken.getName()).get().getPersonId().toString());
        response.put("about", personService.findbyName(authInputToken.getName()).get().getAbout());
        return response;
}
    @Autowired
    public Auth_Controller(PasswordEncoder passwordEncoder, Registration_Service registrationService, AuthenticationManager auth, JWTUtil jwt, Person_Service personService) {
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
        authenticationManager =auth;
        jwtUtil=jwt;
        this.personService = personService;
    }
    @PostMapping("/reg")
    @ResponseBody
    public Map<String, String> performReg(@RequestBody PersonDTO person ){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        registrationService.reg(converttoPerson(person));
        String token = jwtUtil.generateToken(person.getName());
        Map<String, String> response = new HashMap<>();
        response.put("jwt-token", token);
        response.put("name", person.getName());
        response.put("email", person.getEmail());
        return  response;
    }
    private Person converttoPerson(PersonDTO personDTO){
        Person person=new Person();
        person.setName(personDTO.getName());
        person.setGmail(personDTO.getEmail());
        person.setPassword(personDTO.getPassword());
        person.setPersonRole("ROLE_DEF");
        person.setBlock(false);
        return   person;
    }
}
