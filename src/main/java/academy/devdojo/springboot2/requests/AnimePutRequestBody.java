package academy.devdojo.springboot2.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePutRequestBody {


    private Long id;
    @NotNull
    private String name;
}
