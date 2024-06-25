package academy.devdojo.springboot2.handler;


import academy.devdojo.springboot2.exceptions.BadRequestException;
import academy.devdojo.springboot2.exceptions.BadRequestExceptionDetails;
import academy.devdojo.springboot2.exceptions.ValidationExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerExceptionDetails(BadRequestException exception) {

        return new ResponseEntity<> ( BadRequestExceptionDetails.builder ()
                .timestamp ( LocalDateTime.now () )
                .status ( HttpStatus.BAD_REQUEST.value () )
                .title ( "Bad Request Exception, Check the Documentation" )
                .details ( exception.getMessage () )
                .message ( exception.getClass ().getName () ).build (), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult ().getFieldErrors ();
        String fields = fieldErrors.stream ().map ( FieldError::getField ).collect ( Collectors.joining ( "," ) );
        String fieldMessage = fieldErrors.stream ().map ( FieldError::getDefaultMessage ).collect ( Collectors.joining ( "," ) );


        return new ResponseEntity<> (
                ValidationExceptionDetails.builder ()
                        .timestamp ( LocalDateTime.now () )
                        .status ( HttpStatus.BAD_REQUEST.value () )
                        .title ( "Bad Request Exception, Invalid Fields" )
                        .details ( exception.getMessage () )
                        .fields ( fields )
                        .fieldsMessage ( fieldMessage )
                        .message ( exception.getClass ().getName () )
                        .build (), HttpStatus.BAD_REQUEST );

    }
}