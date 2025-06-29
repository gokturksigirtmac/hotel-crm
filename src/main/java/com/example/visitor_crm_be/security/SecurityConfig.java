package com.example.visitor_crm_be.security;

import com.example.visitor_crm_be.error.AuthError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired @Lazy
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //configuration
        http.csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.
                        requestMatchers("/api/visitors").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/visitors/**").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/companies").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/companies/**").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/drivers").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/drivers/**").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/locations").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/locations/**").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/vehicles").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/vehicles/**").hasAuthority("ROLE_ADMIN").
                        requestMatchers("/api/trial").permitAll().
                        requestMatchers("/api/auth/login").permitAll().
                        requestMatchers("/auth/create").permitAll().
                        anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // if any exception came
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // nothing to save on server
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://visitor-crm-fe.vercel.app",
                "https://visitor-crm-5gyoullus-gokturks-projects-a4f3df78.vercel.app"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // İzin verilen header'lar
        configuration.setAllowCredentials(true); // Eğer cookie varsa
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    @Bean
    public AuthError authError() {
        return new AuthError();
    }
}
