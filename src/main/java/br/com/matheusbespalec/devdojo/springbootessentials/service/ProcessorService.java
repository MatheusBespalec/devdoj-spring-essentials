package br.com.matheusbespalec.devdojo.springbootessentials.service;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestException;
import br.com.matheusbespalec.devdojo.springbootessentials.mapper.ProcessorMapper;
import br.com.matheusbespalec.devdojo.springbootessentials.repository.ProcessorRepository;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProcessorService {
    private final ProcessorRepository processorRepository;
    public Page<Processor> list(Pageable pageable) {
        return processorRepository.findAll(pageable);
    }
    public List<Processor> listAll() {
        return processorRepository.findAll();
    }

    public Processor findById(Long id) {
        return  processorRepository.findById(id).orElseThrow(() -> new BadRequestException("Processor not Found"));
    }

    public Processor save(ProcessorPostRequestBody processorPostRequestBody) {
        return processorRepository.save(ProcessorMapper.INSTANCE.toProcessor(processorPostRequestBody));
    }

    public void delete(Long id) {
        processorRepository.delete(findById(id));
    }

    public void replace(ProcessorPutRequestBody processorPutRequestBody) {
        log.info(ProcessorMapper.INSTANCE.toProcessor(processorPutRequestBody));
        findById(processorPutRequestBody.getId());
        processorRepository.save(ProcessorMapper.INSTANCE.toProcessor(processorPutRequestBody));
    }
}
