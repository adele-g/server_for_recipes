package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService implements IUser{

    private UserRepository repository;
    private PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<HttpStatus> save(User user) {

        Optional<User> us = Optional.ofNullable(repository.findByEmail(user.getEmail()));
        if (us.isEmpty()) {//|| !us.get().getEmail().equals(user.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    List<User> getAllUsers() {
        return repository.findAll();
    }
}