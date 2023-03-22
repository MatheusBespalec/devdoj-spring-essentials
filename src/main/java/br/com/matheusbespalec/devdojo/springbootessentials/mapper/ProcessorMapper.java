package br.com.matheusbespalec.devdojo.springbootessentials.mapper;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public abstract class ProcessorMapper {
    public static ProcessorMapper INSTANCE = Mappers.getMapper(ProcessorMapper.class);

    public abstract Processor toProcessor(ProcessorPutRequestBody processorPutRequestBody);
    public abstract Processor toProcessor(ProcessorPostRequestBody processorPostRequestBody);
}
