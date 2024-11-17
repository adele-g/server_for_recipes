package task.recipe.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import task.recipe.domain.Recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface RecipeService {

	Optional<Recipe> findById(long id);
	HashMap<String, Long> save(Recipe user);
	ResponseEntity<HttpStatus> deleteById(long id);
	List<Recipe> getByName(String name, String nameOfColumn);
	void updateRecipe(Recipe toUpdateRecipe, Recipe forUpdateRecipe);
}
