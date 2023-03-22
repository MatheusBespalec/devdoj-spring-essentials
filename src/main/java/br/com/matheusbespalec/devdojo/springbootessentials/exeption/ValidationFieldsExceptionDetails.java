package br.com.matheusbespalec.devdojo.springbootessentials.exeption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
public class ValidationFieldsExceptionDetails extends ExceptionDetails {
    private List<ErrorField> fields = new ArrayList<>();

    @AllArgsConstructor
    @Getter
    public static class ErrorField {
        private String field;
        private String error;
    }
}
