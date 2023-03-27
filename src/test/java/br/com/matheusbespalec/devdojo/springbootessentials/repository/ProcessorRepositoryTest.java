package br.com.matheusbespalec.devdojo.springbootessentials.repository;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProcessorRepositoryTest {
    @Autowired
    private ProcessorRepository processorRepository;

    @Test
    @DisplayName("save persists anime when successful")
    void save_PersistsProcessor_WhenSuccessful() {
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        Processor processorSaved = this.processorRepository.save(processorToBeSaved);

        assertNotNull(processorSaved);
        assertEquals(processorToBeSaved.getName(), processorSaved.getName());
        assertEquals(processorToBeSaved.getCores(), processorSaved.getCores());
        assertEquals(processorToBeSaved.getThreads(), processorSaved.getThreads());
        assertEquals(processorToBeSaved.getBaseClock(), processorSaved.getBaseClock());
    }

    @Test
    @DisplayName("save updates updates processor when successful")
    void save_UpdatesProcessor_WhenSuccessful() {
        Processor processorSaved = this.processorRepository.save(ProcessorCreator.createProcessorToBeSaved());

        processorSaved.setName("Ryzen 7 7700X");
        processorSaved.setCores(8);
        processorSaved.setThreads(16);
        processorSaved.setBaseClock(5.4);

        Processor processorUpdated = this.processorRepository.save(processorSaved);

        assertNotNull(processorUpdated);
        assertEquals(processorSaved.getName(), processorUpdated.getName());
        assertEquals(processorSaved.getCores(), processorUpdated.getCores());
        assertEquals(processorSaved.getThreads(), processorUpdated.getThreads());
        assertEquals(processorSaved.getBaseClock(), processorUpdated.getBaseClock());
    }

    @Test
    @DisplayName("findById returns the processor when successful")
    void findById_ReturnsProcessor_WhenSuccessful() {
        Processor processorSaved = this.processorRepository.save(ProcessorCreator.createProcessorToBeSaved());
        Optional<Processor> processorOptional = this.processorRepository.findById(processorSaved.getId());

        Assertions.assertFalse(processorOptional.isEmpty());

        Processor processor = processorOptional.get();

        assertEquals(processorSaved.getId(), processor.getId());
        assertEquals(processorSaved.getName(), processor.getName());
        assertEquals(processorSaved.getCores(), processor.getCores());
        assertEquals(processorSaved.getThreads(), processor.getThreads());
        assertEquals(processorSaved.getBaseClock(), processor.getBaseClock());
    }

    @Test
    @DisplayName("findById returns empty processor optional when processor not exists")
    void findById_ReturnsEmptyProcessorOptional_WhenProcessorNotExists() {
        Optional<Processor> processorOptional = this.processorRepository.findById(1000L);
        assertTrue(processorOptional.isEmpty());
    }

    @Test
    @DisplayName("find returns list of processors when successful")
    void find_ReturnsListOfProcessors_WhenSuccessful() {
        Processor processorSaved = this.processorRepository.save(ProcessorCreator.createProcessorToBeSaved());
        List<Processor> processorList = this.processorRepository.findAll();

        assertNotNull(processorList);
        assertTrue(processorList.contains(processorSaved));
    }

    @Test
    @DisplayName("delete removes processor when successful")
    void delete_RemovesProcessor_WhenSuccessful() {
        Processor processorSaved = this.processorRepository.save(ProcessorCreator.createProcessorToBeSaved());
        this.processorRepository.deleteById(processorSaved.getId());

        Optional<Processor> processorOptional = this.processorRepository.findById(processorSaved.getId());
        assertTrue(processorOptional.isEmpty());
    }

    @Test
    @DisplayName("save throws ConstraintViolationException when processor name is empty")
    void save_ThrowsConstraintViolationException_WhenProcessorNameIsEmpty() {
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        processorToBeSaved.setName("");
        assertThrows(ConstraintViolationException.class, () -> this.processorRepository.save(processorToBeSaved));
    }
}