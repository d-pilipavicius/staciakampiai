package com.example.demo.security;


import com.example.demo.security.ExceptionHandling.CustomAccessHandler;
import com.example.demo.security.filters.CustomJwtExceptionFilter;
import com.example.demo.security.filters.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private JwtAuthEntryPoint entryPoint;

    private CustomAccessHandler customAccessHandler;

    private JWTUtils jwtUtils;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/error/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOriginPattern("http://localhost:5173");
                    config.addAllowedOriginPattern("http://localhost:3000");
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/v1/orders/**").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers("/v1/reservations/**").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers("/h2-console/**").permitAll() //used for h2 to be accessible
                        .requestMatchers(HttpMethod.POST, "/v1/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "v1/business/*").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers( "v1/user/**").hasAuthority("ITAdministrator")
                        .requestMatchers(HttpMethod.PUT,"v1/business/*").hasAuthority("BusinessOwner")
                        .requestMatchers("v1/business/**").hasAuthority("ITAdministrator")
                        .requestMatchers(HttpMethod.GET, "v1/**").hasAnyAuthority("BusinessOwner", "Employee")
                        //if need to add some DELETE method which needs to get accessed by Employee add below comment
                        .requestMatchers(HttpMethod.DELETE, "v1/**").hasAuthority("BusinessOwner")
                        //if need to add some POST method which needs to get accessed by Employee add below comment
                        .requestMatchers(HttpMethod.POST, "v1/discounts/*").hasAuthority("BusinessOwner")
                        //if need to add some Put method which needs to get accessed by Employee add below comment
                        .requestMatchers(HttpMethod.PUT, "v1/discounts/*/increaseUsage").hasAnyAuthority("BusinessOwner", "Employee")
                        .requestMatchers(HttpMethod.PUT, "v1/**").hasAuthority("BusinessOwner")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(customAccessHandler)
                )
                .headers(HeadersConfigurer::disable); //used for h2, after real database delete!!!!
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter(), JwtFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
