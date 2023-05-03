package com.sigma.coursesMS.config;

import com.sigma.coursesMS.service.TeacherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.xxrnjilywmnxqcke.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


/*
* This class was build using spring boot 3, so
* No need to extend  WebSecurityConfiguration nor to add additional annotation
* All set by default
* */
@Configuration // Security configuration class
public class SecurityConfig{

    // Injecting Teacher server that implements UserDetailsService
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder; // inject PasswordEncoder

    // Constructor injection
    public SecurityConfig(TeacherService teacherService, PasswordEncoder passwordEncoder) {
        this.teacherService = teacherService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean // Connection the Teacher in database to be used as UserDetailsService
    public DaoAuthenticationConfigurer<AuthenticationManagerBuilder, TeacherService> configure (AuthenticationManagerBuilder auth) throws Exception {
        return auth.userDetailsService(teacherService).passwordEncoder(passwordEncoder);
    }


    @Bean // Configure security filter (Authentication and authorization)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer // filter the allowed endpoints (any endpoint is not included is inaccessible for all by default)
                        .requestMatchers(HttpMethod.GET,"account").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST,"account").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET,"addcourse").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "courses").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "courses/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "courses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "courses").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT, "courses/**").hasRole("TEACHER")
                        .requestMatchers("/signup").permitAll() // allowed for all
                        .requestMatchers("/reset-password").permitAll() // allowed for all
                        .requestMatchers("/enter-otp").permitAll() // allowed for all
                        .requestMatchers("/new-password").permitAll() // allowed for all
                        .requestMatchers("/error").permitAll() // allowed for all
                        .requestMatchers("/images/**").permitAll() // allowed for all

                )
                .formLogin(login -> // login configuration
                        login.
                                loginPage("/login") // costumed login form path (GET request)
                                .permitAll()  // access for all
                                .defaultSuccessUrl("/courses") // if authenticated correctly redirect to "/courses"
                )
                .logout(logout -> // logout configuration
                        logout. // once logout, authentication in cancelled totally
                                logoutUrl("/logout") // logout path is handled by Spring security (no controller handler)
                                .logoutSuccessUrl("/login") // if logout done, redirect ot the login page
                                .invalidateHttpSession(true) // prevent any session after logout
                )
                .httpBasic() // use http basic authentication, so the user's credentials are sent in the header of each HTTP request.
                .and() // http.
                .sessionManagement(session -> //session configuration
                                session
                                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // new session will be created for each request.
                                        .invalidSessionUrl("/login") // redirect the user to if an invalid session is detected.
                                        .sessionFixation().migrateSession() //protect and prevent "Fixation attack"
                                        .maximumSessions(1) // limit the number of sessions (login) that one user may have at the same time to 1
                                        .maxSessionsPreventsLogin(true) // prevent any login for when exceeding the limit
                                        .expiredUrl("/login?expired") // redirect after the session is experienced

                );

        http.cors().and().csrf().disable(); // protect and prevent csrf anf cors attack

        return http.build(); // build the security filter
    }


    // configure Spring security session to be used in filter (used in session management features)
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}
