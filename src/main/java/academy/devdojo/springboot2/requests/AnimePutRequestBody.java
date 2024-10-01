package academy.devdojo.springboot2.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePutRequestBody {
    @NotNull(message = "The ID can't be null")
    private Long id;
    @NotBlank(message = "The name can't be empty")
    private String name;
}
