package br.com.matheusbespalec.devdojo.springbootessentials.mapper;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-09T17:34:30-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
@Component
public class ProcessorMapperImpl extends ProcessorMapper {

    @Override
    public Processor toProcessor(ProcessorPutRequestBody processorPutRequestBody) {
        if ( processorPutRequestBody == null ) {
            return null;
        }

        Processor.ProcessorBuilder processor = Processor.builder();

        processor.id( processorPutRequestBody.getId() );
        processor.name( processorPutRequestBody.getName() );
        processor.cores( processorPutRequestBody.getCores() );
        processor.threads( processorPutRequestBody.getThreads() );
        processor.baseClock( processorPutRequestBody.getBaseClock() );

        return processor.build();
    }

    @Override
    public Processor toProcessor(ProcessorPostRequestBody processorPostRequestBody) {
        if ( processorPostRequestBody == null ) {
            return null;
        }

        Processor.ProcessorBuilder processor = Processor.builder();

        processor.name( processorPostRequestBody.getName() );
        processor.cores( processorPostRequestBody.getCores() );
        processor.threads( processorPostRequestBody.getThreads() );
        processor.baseClock( processorPostRequestBody.getBaseClock() );

        return processor.build();
    }
}
