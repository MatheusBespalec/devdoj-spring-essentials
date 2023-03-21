package br.com.matheusbespalec.devdojo.springbootessentials.exeption;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetails extends  RuntimeException {
    private String title;
    private Integer status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
