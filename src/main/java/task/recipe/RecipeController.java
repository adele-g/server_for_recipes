package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeController {
	private final RecipeService service;
	private final UserService userService;

	@Autowired
	public RecipeController(RecipeService service, UserService userService) {
		this.service = service;
		this.userService = userService;
	}

	@GetMapping("/recipe/{id}")
	public ResponseEntity<Recipe> getRecipe(@PathVariable("id") @Valid @Min(1) long id) {
		Recipe recipe = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}

	@GetMapping("/recipe/search")
	public List<Recipe> getRecipeSearch(@RequestParam(required = false) Optional<String> category,
										@RequestParam(required = false) Optional<String> name) {
		if (category.isEmpty() && name.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (category.isPresent()) {
			return service.searchByName(category.get(), "category");
		}
		return service.searchByName(name.get(), "name");

	}

	@GetMapping("/recipe/users")
	public List<User> users() {
		return userService.getAllUsers();
	}

	@GetMapping("/recipe/recipes")
	public List<Recipe> recipes() {
		return service.findAll();
	}

	@PostMapping("/register")
	public ResponseEntity<HttpStatus> register(@Valid @NotNull @NotBlank @RequestBody @Autowired User user) {
		return userService.save(user);
	}

	@PostMapping("/recipe/new")
	public HashMap<String, Long> postRecipe(@Valid @NotNull @NotBlank @RequestBody @Autowired Recipe recipe) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		recipe.setEmail(auth.getName());
		return service.save(recipe);
	}

	@PutMapping("/recipe/{id}")
	public ResponseEntity<HttpStatus> putRecipeById(@RequestBody @NotNull @NotBlank @Valid Recipe recipe,
													@PathVariable("id") @Valid @Min(1) long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Recipe recipeFromService = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (auth.getName().equals(recipeFromService.getEmail())) {

			recipeFromService.setName(recipe.getName());
			recipeFromService.setCategory(recipe.getCategory());
			recipeFromService.setDate(recipe.getDate());
			recipeFromService.setDescription(recipe.getDescription());
			recipeFromService.setIngredients(recipe.getIngredients());
			recipeFromService.setDirections(recipe.getDirections());
			service.save(recipeFromService);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@DeleteMapping("/recipe/{id}")
	public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") @Min(1) long id) {
		return service.deleteById(id);
	}
}