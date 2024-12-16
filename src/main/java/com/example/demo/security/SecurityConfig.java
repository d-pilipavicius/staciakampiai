package com.example.demo.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private JwtAuthEntryPoint entryPoint;

    private CustomUserDetailsService customUserDetailsService;

    private JWTUtils jwtUtils;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/h2-console/**").permitAll() //used for h2 to be accessible
                        .requestMatchers(HttpMethod.POST, "/v1/user/login").permitAll()
                        .requestMatchers( "v1/user/**").permitAll() //for now to test, later on make only Admin
                        .requestMatchers("v1/business/**").hasAuthority("ITAdministrator")
                        .requestMatchers(HttpMethod.GET, "v1/**").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers(HttpMethod.DELETE, "v1/**").hasAuthority("BusinessOwner")
                        .requestMatchers(HttpMethod.POST, "v1/discounts/*").hasAuthority("BusinessOwner")
                        .requestMatchers(HttpMethod.PUT, "v1/discounts/*/increaseUsage").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers(HttpMethod.PUT, "v1/**").hasAuthority("BusinessOwner")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(entryPoint))
                .headers(HeadersConfigurer::disable); //used for h2, after real database delete!!!!
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public JwtFilter jwtFilter(){
        return new JwtFilter(jwtUtils);
    }

    public CustomJwtExceptionFilter jwtExceptionFilter(){return new CustomJwtExceptionFilter();}
}
