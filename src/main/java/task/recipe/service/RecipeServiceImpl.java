package task.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import task.recipe.domain.Recipe;
import task.recipe.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.*;

import static task.recipe.controller.RecipeController.CATEGORY;
import static task.recipe.controller.RecipeController.NAME;

@Service
public class RecipeServiceImpl implements task.recipe.interfaces.RecipeService {

	final RecipeRepository repository;

	@Autowired
	public RecipeServiceImpl(RecipeRepository repository) {
		this.repository = repository;
	}

	public List<Recipe> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Recipe> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public HashMap<String, Long> save(Recipe recipe) {
		HashMap<String, Long> result = new HashMap<>();

		recipe.setDate(LocalDateTime.now());
		Recipe savedRecipe = repository.save(recipe);
		result.put("id", savedRecipe.getId());
        return result;
	}

	@Override
	public ResponseEntity<HttpStatus> deleteById(long id) {
		Recipe recipe = repository.
				findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals(recipe.getEmail())) {
			repository.deleteById(id);
			if (repository.findById(id).isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@Override
	public List<Recipe> getByName(String name, String nameOfColumn) {
		ArrayList<Recipe> result = new ArrayList<>();

		repository.findAll().forEach(recipe -> {
			if (nameOfColumn.equals(CATEGORY)) {
				if (recipe.getCategory().equalsIgnoreCase(name)) {
					result.add(recipe);
				}
			}
			else if (nameOfColumn.equals(NAME)) {
				if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
					result.add(recipe);
				}
			}
		});
		result.sort(Comparator.comparing(Recipe::getDate).reversed());
		return result;
	}

	@Override
	public void updateRecipe(Recipe recipeToUpdate, Recipe forUpdateRecipe) {
		enrichRecipe(forUpdateRecipe, recipeToUpdate);
		save(forUpdateRecipe);
	}

	private void enrichRecipe(Recipe recipe, Recipe recipeFromService) {
		recipeFromService.setName(recipe.getName());
		recipeFromService.setCategory(recipe.getCategory());
		recipeFromService.setDate(recipe.getDate());
		recipeFromService.setDescription(recipe.getDescription());
		recipeFromService.setIngredients(recipe.getIngredients());
		recipeFromService.setDirections(recipe.getDirections());
	}
}
