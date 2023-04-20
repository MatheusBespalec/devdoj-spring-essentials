package br.com.matheusbespalec.devdojo.springbootessentials.util;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;

public class ProcessorPutRequestBodyCreator {
    public static ProcessorPutRequestBody createProcessorRequestBody() {
        Processor validProcessor = ProcessorCreator.createValidProcessor();
        return ProcessorPutRequestBody.builder()
                .id(validProcessor.getId())
                .name(validProcessor.getName())
                .cores(validProcessor.getCores())
                .threads(validProcessor.getThreads())
                .baseClock(validProcessor.getBaseClock())
                .build();
    }
}
