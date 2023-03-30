package br.com.matheusbespalec.devdojo.springbootessentials.util;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;

public class ProcessorPostRequestBodyCreator {
    public static ProcessorPostRequestBody createProcessorRequestBody() {
        Processor validProcessor = ProcessorCreator.createValidProcessor();
        return ProcessorPostRequestBody.builder()
                .name(validProcessor.getName())
                .cores(validProcessor.getCores())
                .threads(validProcessor.getThreads())
                .baseClock(validProcessor.getBaseClock())
                .build();
    }
}
