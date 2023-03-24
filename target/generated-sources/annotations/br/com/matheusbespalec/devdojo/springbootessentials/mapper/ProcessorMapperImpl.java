package br.com.matheusbespalec.devdojo.springbootessentials.mapper;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-21T21:39:20-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
public class ProcessorMapperImpl extends ProcessorMapper {

    @Override
    public Processor toProcessor(ProcessorPutRequestBody processorPutRequestBody) {
        if ( processorPutRequestBody == null ) {
            return null;
        }

        Processor processor = new Processor();

        processor.setId( processorPutRequestBody.getId() );
        processor.setName( processorPutRequestBody.getName() );
        processor.setCores( processorPutRequestBody.getCores() );
        processor.setThreads( processorPutRequestBody.getThreads() );
        processor.setBaseClock( processorPutRequestBody.getBaseClock() );

        return processor;
    }

    @Override
    public Processor toProcessor(ProcessorPostRequestBody processorPostRequestBody) {
        if ( processorPostRequestBody == null ) {
            return null;
        }

        Processor processor = new Processor();

        processor.setName( processorPostRequestBody.getName() );
        processor.setCores( processorPostRequestBody.getCores() );
        processor.setThreads( processorPostRequestBody.getThreads() );
        processor.setBaseClock( processorPostRequestBody.getBaseClock() );

        return processor;
    }
}
