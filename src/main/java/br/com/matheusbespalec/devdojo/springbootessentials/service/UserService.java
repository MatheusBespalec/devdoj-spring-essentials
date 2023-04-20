package br.com.matheusbespalec.devdojo.springbootessentials.service;

import br.com.matheusbespalec.devdojo.springbootessentials.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(this.userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
