package com.example.demo.Security;
import com.example.demo.Entities.Person;
import com.example.demo.Repository.Person_Repository;
import com.example.demo.Service.Person_Details_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final Person_Details_Service personDetailsService;
    private final Person_Repository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProvider(Person_Details_Service personDetailsService,
                                        Person_Repository personRepository,
                                        PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(org.springframework.security.core.Authentication authentication) {
        String username = authentication.getName();
        String password = null;
        if (authentication.getCredentials() != null) {
            password = authentication.getCredentials().toString();
        }
        Optional<Person> personOptional = personRepository.findByName(username);
            if (personOptional.isPresent()) {
            Person person = personOptional.get();
            if (passwordEncoder.matches(password, person.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, password, personDetailsService.loadUserByUsername(username).getAuthorities());
            }
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
