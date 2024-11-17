package task.recipe.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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
@Data
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
}