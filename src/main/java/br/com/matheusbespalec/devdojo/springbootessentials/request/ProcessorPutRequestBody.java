package br.com.matheusbespalec.devdojo.springbootessentials.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProcessorPutRequestBody {
    @Min(1)
    private Long id;
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
