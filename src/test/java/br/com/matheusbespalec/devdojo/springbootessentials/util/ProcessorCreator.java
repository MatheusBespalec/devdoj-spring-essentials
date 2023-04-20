package br.com.matheusbespalec.devdojo.springbootessentials.util;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;

public class ProcessorCreator {
    public static Processor createProcessorToBeSaved() {
        return Processor.builder()
                .name("Ryzen 7 7700X")
                .cores(8)
                .threads(16)
                .baseClock(5.4)
                .build();
    }

    public static Processor createValidProcessor() {
        return Processor.builder()
                .id(1L)
                .name("Ryzen 7 7700X")
                .cores(8)
                .threads(16)
                .baseClock(5.4)
                .build();
    }
}
