package task.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IRecipe {

	Optional<Recipe> findById(long id);
	HashMap<String, Long> save(Recipe user);
	ResponseEntity<HttpStatus> deleteById(long id);
	List<Recipe> searchByName(String name, String nameOfColumn);

}
