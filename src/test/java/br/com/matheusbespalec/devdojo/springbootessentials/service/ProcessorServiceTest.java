package br.com.matheusbespalec.devdojo.springbootessentials.service;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.exeption.BadRequestException;
import br.com.matheusbespalec.devdojo.springbootessentials.repository.ProcessorRepository;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorCreator;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorPostRequestBodyCreator;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorPutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProcessorServiceTest {
    @InjectMocks
    private ProcessorService processorService;

    @Mock
    private ProcessorRepository processorRepositoryMoock;

    @BeforeEach
    void setup() {
        PageImpl<Processor> page = new PageImpl<>(List.of(ProcessorCreator.createValidProcessor()));
        BDDMockito.when(processorRepositoryMoock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(page);
        BDDMockito.when(processorRepositoryMoock.findAll()).thenReturn(List.of(ProcessorCreator.createValidProcessor()));
        BDDMockito.when(processorRepositoryMoock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ProcessorCreator.createValidProcessor()));
        BDDMockito.when(processorRepositoryMoock.save(ArgumentMatchers.any(Processor.class)))
                .thenReturn(ProcessorCreator.createValidProcessor());
        BDDMockito.doNothing().when(processorRepositoryMoock).delete(ArgumentMatchers.any(Processor.class));
    }

    @Test
    @DisplayName("list")
    void list_ReturnsPageOfProcessor_WhenSuccess() {
        Page<Processor> processorPage = processorService.list(PageRequest.of(1, 1));

        assertNotNull(processorPage);
        assertTrue(processorPage.getTotalElements() > 0);
    }

    @Test
    @DisplayName("listAll returns list of processors when successful")
    void listAll_ReturnsListOfProcessors_WhenSuccessful() {
        List<Processor> processorList = processorService.listAll();

        assertNotNull(processorList);
        assertFalse(processorList.isEmpty());
    }

    @Test
    @DisplayName("findById returns processor when successful")
    void findById_ReturnsProcessor_WhenSuccessful() {
        Processor processor = ProcessorCreator.createValidProcessor();
        Processor processorFound = processorService.findById(1L);

        assertNotNull(processorFound);
        assertEquals(processorFound, processor);
    }

    @Test
    @DisplayName("findById throws BadRequestException when processor is not found")
    void findById_ThrowsBadRequestException_WhenProcessorIsNotFound() {
        BDDMockito.when(processorRepositoryMoock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> processorService.findById(1L));
    }

    @Test
    @DisplayName("save returns processor saved when successful")
    void save_ReturnsProcessorSaved_WhenSuccessful() {
        ProcessorPostRequestBody processorToBeSaved = ProcessorPostRequestBodyCreator.createProcessorRequestBody();
        Processor processorSaved = processorService.save(processorToBeSaved);

        assertNotNull(processorSaved);
        assertNotNull(processorSaved.getId());
        assertEquals(processorSaved.getName(), processorToBeSaved.getName());
        assertEquals(processorSaved.getCores(), processorToBeSaved.getCores());
        assertEquals(processorSaved.getThreads(), processorToBeSaved.getThreads());
        assertEquals(processorSaved.getBaseClock(), processorToBeSaved.getBaseClock());
    }

    @Test
    @DisplayName("delete removes processor when successful")
    void delete_RemovesProcessor_WhenSuccessful() {
        assertDoesNotThrow(() -> processorService.delete(ProcessorCreator.createValidProcessor().getId()));
    }

    @Test
    @DisplayName("replace updates processor when successful")
    void replace_UpdatesProcessor_WhenSuccessful() {
        assertDoesNotThrow(() -> processorService.replace(ProcessorPutRequestBodyCreator.createProcessorRequestBody()));
    }
}