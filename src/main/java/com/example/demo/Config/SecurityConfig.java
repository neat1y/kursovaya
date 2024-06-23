package com.example.demo.Config;

import com.example.demo.Entities.Person;
import com.example.demo.Repository.Person_Repository;
import com.example.demo.Security.AuthProvider;
import com.example.demo.Security.PersonDetails;
import com.example.demo.Service.Person_Details_Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.example.demo.Service.Person_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private final Person_Details_Service personDetailsService;
    private final Person_Repository personRepository;
    private final JWTFilter jwtFilter;



    @Autowired
    public SecurityConfig(Person_Details_Service personDetailsService, Person_Repository personRepository, JWTFilter jwtFilter) {
        this.personDetailsService = personDetailsService;
        this.personRepository = personRepository;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Разрешить запросы только из этого источника
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные HTTP методы
                .allowedHeaders("*");
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/**");
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/curs/image").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/reg").permitAll()
                .antMatchers("/curs/user/image/**").permitAll()
                .antMatchers("/curs/image/**").permitAll()
                .antMatchers("/curs/students/**").hasAnyRole("ROLE_DEF","ROLE_ADMIN","DEF","ADMIN")
                .antMatchers("/curs/students").hasAnyRole("ROLE_DEF","ROLE_ADMIN","DEF","ADMIN")
                .antMatchers("/curs/**").hasAnyRole("ROLE_DEF","ROLE_ADMIN","DEF","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/curs/students", true)
                .failureUrl("/auth/login?error")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthProvider customAuthenticationProvider() {
        return new AuthProvider(personDetailsService, personRepository, getPasswordEncoder());
    }
}
