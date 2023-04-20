package br.com.matheusbespalec.devdojo.springbootessentials.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Log4j2
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/processors/admin/**").hasRole("ADMIN")
                .antMatchers("/processors/**").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}
