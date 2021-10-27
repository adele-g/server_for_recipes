package task.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IUser {
    ResponseEntity<HttpStatus> save(User user);
}
