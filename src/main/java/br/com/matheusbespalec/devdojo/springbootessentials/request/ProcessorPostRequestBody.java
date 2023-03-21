package br.com.matheusbespalec.devdojo.springbootessentials.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ProcessorPostRequestBody {
    @NotEmpty
    private String name;
    @NotNull
    @Min(1)
    private Integer cores;
    @NotNull
    @Min(0)
    private Integer threads;
    @NotNull
    @DecimalMin("0.1")
    private Double baseClock;
}
