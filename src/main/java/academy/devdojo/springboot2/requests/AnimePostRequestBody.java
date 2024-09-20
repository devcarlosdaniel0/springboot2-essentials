package academy.devdojo.springboot2.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnimePostRequestBody {
    @NotBlank(message = "The name can't be empty")
    private String name;
}
