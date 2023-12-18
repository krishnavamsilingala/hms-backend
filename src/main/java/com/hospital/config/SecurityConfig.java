package com.hospital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

//        http
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                //.httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.cors().disable();
        http
                .authorizeRequests()
                .antMatchers("/api/login").permitAll() // Permit access to public endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
        http.addFilter(corsFilter());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // For demo purposes, not for production
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedOrigin("https://141f-2405-201-d022-d06f-6d9f-b741-de9a-324.ngrok-free.app");
        config.setAllowedOriginPatterns(List.of("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Methods");
        config.addExposedHeader("Access-Control-Allow-Headers");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
