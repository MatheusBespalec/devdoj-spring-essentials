package br.com.matheusbespalec.devdojo.springbootessentials.controller;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/processors")
@RequiredArgsConstructor
@Log4j2
public class ProcessorController {
    private final ProcessorService processorService;

    @GetMapping
    public ResponseEntity<Page<Processor>> list(Pageable pageable) {
        return ResponseEntity.ok(processorService.list(pageable)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Processor>> listAll() {
        return ResponseEntity.ok(processorService.listAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processor> findById(@PathVariable Long id) {
        return ResponseEntity.ok(processorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Processor> save(@RequestBody @Valid ProcessorPostRequestBody processorPostRequestBody) {
        return new ResponseEntity<Processor>(processorService.save(processorPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        processorService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid ProcessorPutRequestBody processorPutRequestBody) {
        processorService.replace(processorPutRequestBody);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
