package academy.devdojo.springboot2.handler;


import academy.devdojo.springboot2.exceptions.BadRequestException;
import academy.devdojo.springboot2.exceptions.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {



    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails>handlerExceptionDetails(BadRequestException exception){

        return  new ResponseEntity<> ( BadRequestExceptionDetails.builder ()
                .timestamp ( LocalDateTime.now () )
                .status ( HttpStatus.BAD_REQUEST.value () )
                .title ( "Bad Request Exception, Check the Documentation" )
                .details ( exception.getMessage () )
                .message ( exception.getClass ().getName () ).build (),HttpStatus.BAD_REQUEST);



    }
}