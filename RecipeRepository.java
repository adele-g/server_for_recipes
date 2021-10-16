package task.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.function.Predicate;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
