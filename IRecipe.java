package task.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IRecipe {

	List<Recipe> getAllUsers();
	Optional<Recipe> findById(long id);
	HashMap<String, Long> save(Recipe user);
	void deleteById(long id);
}
