package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements IRecipe{

	RecipeRepository repository;

	@Autowired
	public RecipeService(RecipeRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Recipe> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public Optional<Recipe> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public HashMap<String, Long> save(Recipe recipe) {
		HashMap<String, Long> result = new HashMap<>();
		result.put("id", repository.save(recipe).getId());
		return result;
	}

	@Override
	public void deleteById(long id) {
		Optional<Recipe> recipe = repository.findById(id);
		recipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		repository.deleteById(id);
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}
}
