package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeService implements IRecipe{

	RecipeRepository repository;

	@Autowired
	public RecipeService(RecipeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Recipe> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public HashMap<String, Long> save(Recipe recipe) {
		HashMap<String, Long> result = new HashMap<>();
		recipe.setDate(LocalDateTime.now());
		result.put("id", repository.save(recipe).getId());
		return result;
	}

	@Override
	public ResponseEntity<HttpStatus> deleteById(long id) {
		Optional<Recipe> recipe = repository.findById(id);
		recipe.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Recipe> searchByName(String name, String nameOfColumn) {
		ArrayList<Recipe> result = new ArrayList<>();

		repository.findAll().forEach(recipe -> {
			if (nameOfColumn.equals("category")) {
				if (recipe.getCategory().equalsIgnoreCase(name)) {
					result.add(recipe);
				}
			}
			else if (nameOfColumn.equals("name")) {
				if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(recipe);
				}
			}
		});
		result.sort(Comparator.comparing(Recipe::getDate).reversed());
		return result;
	}
}
