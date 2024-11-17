package task.recipe.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import task.recipe.domain.User;

public interface UserService {
    ResponseEntity<HttpStatus> save(User user);
}
