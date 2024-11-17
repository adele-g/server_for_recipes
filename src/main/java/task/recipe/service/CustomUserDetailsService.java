package task.recipe.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import task.recipe.domain.User;
import task.recipe.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String USER = "USER";
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(repository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));
        return org.springframework.security.core.userdetails
                .User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(USER)
                .build();
    }
}
