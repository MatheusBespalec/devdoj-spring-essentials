package br.com.matheusbespalec.devdojo.springbootessentials.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user2 = User.withUsername("user")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test"))
                .roles("USER")
                .build();
        UserDetails user = User.withUsername("admin")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }
}
