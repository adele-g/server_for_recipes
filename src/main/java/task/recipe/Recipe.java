package task.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Validated
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
	@GenericGenerator(name = "seq", strategy="increment")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private long id;

	@Column
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;

	@Column
	@NotNull
	@NotBlank
	@NotEmpty
	private String category;

	@Column
	private LocalDateTime date;

	@Column
	@NotNull
	@NotBlank
	@NotEmpty
	private String description;

	@ElementCollection
	@NotEmpty
	@Size(min = 1)
	private List<String> ingredients = new ArrayList<>();

	@ElementCollection
	@NotEmpty
	@Size(min = 1)
	private List<String> directions = new ArrayList<>();

	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Recipe() {}

	public Recipe(String name, String category, LocalDateTime date, String description,
				  List<String> ingredients, List<String> directions, String email){
		this.name = name;
		this.category = category;
		this.date = date;
		this.description = description;
		this.ingredients = ingredients;
		this.directions = directions;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public  List<String>  getIngredients() {
		return ingredients;
	}

	public void setIngredients( List<String>  ingredients) {
		this.ingredients = ingredients;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getDirections() {
		return directions;
	}

	public void setDirections(List<String> directions) {
		this.directions = directions;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}