package academy.devdojo.springboot2.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

   protected String title;
   protected int status;
   protected String details;
   protected String message;
   protected LocalDateTime timestamp;
}
