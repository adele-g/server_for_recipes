package task.recipe.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
@Validated
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @Column
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 8)
    private String password;
}
