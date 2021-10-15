package task.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;


@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
	private final RecipeService service;

	@Autowired
	public RecipeController(RecipeService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public Recipe getRecipe(@PathVariable("id") @Min(1) long id) {
		return service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/new")
	public HashMap<String, Long> postRecipe(@Valid @RequestBody @Autowired Recipe recipe) {
		return service.save(recipe);
	}

	@DeleteMapping("/{id}")
	public void deleteRecipe(@PathVariable("id") @Min(1) long id) {
		service.deleteById(id);
	}
}
