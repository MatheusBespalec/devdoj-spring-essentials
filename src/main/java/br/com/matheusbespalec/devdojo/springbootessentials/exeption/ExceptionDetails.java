package br.com.matheusbespalec.devdojo.springbootessentials.exeption;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected Integer status;
    protected String message;
    protected LocalDateTime timestamp;
}
