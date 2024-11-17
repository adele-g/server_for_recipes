package task.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task.recipe.domain.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();
}