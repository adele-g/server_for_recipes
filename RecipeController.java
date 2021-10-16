package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
	private final RecipeService service;

	@Autowired
	public RecipeController(RecipeService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Recipe getRecipe(@PathVariable("id") @Valid @Min(1) long id) {
		return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/search")
	public List<Recipe> getRecipeSearch(@RequestParam(required = false) Optional<String> category,
	                                    @RequestParam(required = false) Optional<String> name) {

		if (category.isEmpty() && name.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		if (category.isPresent()) {
			return service.searchByName(category.get(), "category");
		}
		else if (name.isPresent()) {
			return service.searchByName(name.get(), "name");
		}

		return new ArrayList<>();
	}

	@PostMapping("/new")
	public HashMap<String, Long> postRecipe(@Valid @RequestBody @Autowired Recipe recipe) {
		return service.save(recipe);
	}

	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> putRecipeById(@RequestBody @Valid Recipe recipe, @PathVariable("id") @Valid @Min(1) long id) {
		Recipe recipeFromService = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		recipeFromService.setName(recipe.getName());
		recipeFromService.setCategory(recipe.getCategory());
		recipeFromService.setDate(recipe.getDate());
		recipeFromService.setDescription(recipe.getDescription());
		recipeFromService.setIngredients(recipe.getIngredients());
		recipeFromService.setDirections(recipe.getDirections());

		service.save(recipeFromService);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") @Min(1) long id) {
		return service.deleteById(id);
	}
}
