package br.com.matheusbespalec.devdojo.springbootessentials.exeption;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {

}
