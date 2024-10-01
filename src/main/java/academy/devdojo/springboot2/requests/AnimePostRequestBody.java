package academy.devdojo.springboot2.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
    @NotBlank(message = "The name can't be empty")
    private String name;
}
