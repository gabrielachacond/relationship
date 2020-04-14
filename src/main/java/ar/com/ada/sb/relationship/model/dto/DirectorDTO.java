package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDTO {

    public Long id;

    @NotBlank(message = "name is required")
    public String name;

    @NotBlank(message = "bio is required")
    public String bio;

    @JsonIgnoreProperties(value = "director")
    private List<FilmDTO> films;
}