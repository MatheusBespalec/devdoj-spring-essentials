package br.com.matheusbespalec.devdojo.springbootessentials.handler;

import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestException;
import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestExceptionDetails;
import br.com.matheusbespalec.devdojo.springbootessentials.exeption.ValidationFieldsExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestRequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException) {
        BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails.builder()
                .title("Bad Request Exception, Check the documentation")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(badRequestException.getMessage())
                .build();
        return new ResponseEntity<>(badRequestExceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ValidationFieldsExceptionDetails.ErrorField> errors = new ArrayList<ValidationFieldsExceptionDetails.ErrorField>();
        for (ObjectError error: exception.getBindingResult().getAllErrors()) {
            errors.add(new ValidationFieldsExceptionDetails.ErrorField(
                ((FieldError) error).getField(),
                error.getDefaultMessage()
            ));
        }

        ValidationFieldsExceptionDetails validationFieldsExceptionDetails = ValidationFieldsExceptionDetails.builder()
                .title("Bad Request Exception, Fields Validation")
                .message("check the field(s) error")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .fields(errors)
                .build();

        return super.handleExceptionInternal(exception, validationFieldsExceptionDetails, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails.builder()
                .title(exception.getCause().getMessage())
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(badRequestExceptionDetails, headers, status);
    }
}
