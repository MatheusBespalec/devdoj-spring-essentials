package br.com.matheusbespalec.devdojo.springbootessentials.handler;

import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestException;
import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestRequestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(BadRequestExceptionDetails.builder()
                .title("Bad Request Exception, Check the documentation")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .details(badRequestException.getMessage())
                .developerMessage(badRequestException.getClass().getName())
                .build(),
            HttpStatus.BAD_REQUEST);
    }
}
