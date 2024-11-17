package task.recipe.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import task.recipe.interfaces.UserService;
import task.recipe.domain.User;
import task.recipe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private PasswordEncoder encoder;

    @Override
    public ResponseEntity<HttpStatus> save(User user) {
        Optional<User> us = Optional.ofNullable(repository.findByEmail(user.getEmail()));
        if (us.isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}