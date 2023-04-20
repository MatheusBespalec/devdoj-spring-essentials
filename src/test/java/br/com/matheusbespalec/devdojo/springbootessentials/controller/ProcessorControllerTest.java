package br.com.matheusbespalec.devdojo.springbootessentials.controller;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPostRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.request.ProcessorPutRequestBody;
import br.com.matheusbespalec.devdojo.springbootessentials.service.ProcessorService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProcessorControllerTest {
    @InjectMocks
    private ProcessorController processorController;

    @Mock
    private ProcessorService processorServiceMock;

    @BeforeEach
    void setup() {
        PageImpl<Processor> processorPage = new PageImpl<>(List.of(ProcessorCreator.createValidProcessor()));
        BDDMockito.when(processorServiceMock.list(ArgumentMatchers.any())).thenReturn(processorPage);
        BDDMockito.when(processorServiceMock.listAll()).thenReturn(List.of(ProcessorCreator.createValidProcessor()));
        BDDMockito.when(processorServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(ProcessorCreator.createValidProcessor());
        BDDMockito.when(processorServiceMock.save(ArgumentMatchers.any(ProcessorPostRequestBody.class)))
                .thenReturn(ProcessorCreator.createValidProcessor());
        BDDMockito.doNothing().when(processorServiceMock).delete(ArgumentMatchers.anyLong());
        BDDMockito.doNothing().when(processorServiceMock)
                .replace(ProcessorPutRequestBodyCreator.createProcessorRequestBody());
    }

    @Test
    @DisplayName("list returns list of processor when successful")
    void list_ReturnsPageOfProcessor_WhenSuccessful() {
        String expectedName = ProcessorCreator.createValidProcessor().getName();
        Page<Processor> processorPage = processorController.list(null).getBody();

        assertNotNull(processorPage);

        List<Processor> processorList = processorPage.toList();

        assertFalse(processorList.isEmpty());
        assertEquals(processorList.size(), 1);
        assertEquals(processorList.get(0).getName(), expectedName);
    }

    @Test
    @DisplayName("listAll returns list of processor when successful")
    void listAll_ReturnsListOfProcessor_WhenSuccessful() {
        List<Processor> processorList = this.processorController.listAll().getBody();

        assertNotNull(processorList);
        assertNotNull(processorList.get(0));
        assertNotNull(processorList.get(0).getId());
    }

    @Test
    @DisplayName("findById returns processor when successful")
    void findById_ReturnsProcessor_WhenSuccessful() {
        Processor processor = ProcessorCreator.createValidProcessor();
        Processor processorFound = this.processorController.findById(processor.getId()).getBody();

        assertNotNull(processorFound);
        assertEquals(processorFound.getId(), processor.getId());
        assertEquals(processorFound.getName(), processor.getName());
        assertEquals(processorFound.getCores(), processor.getCores());
        assertEquals(processorFound.getThreads(), processor.getThreads());
        assertEquals(processorFound.getBaseClock(), processor.getBaseClock());
    }

    @Test
    @DisplayName("save returns processor when successful")
    void save_ReturnsProcessor_WhenSuccessful() {
        Processor processorSaved = this.processorController.save(ProcessorPostRequestBodyCreator.createProcessorRequestBody()).getBody();

        assertNotNull(processorSaved);
        assertEquals(processorSaved, ProcessorCreator.createValidProcessor());
    }

    @Test
    @DisplayName("delete returns no content when successful")
    void delete_ReturnsNoContent_WhenSuccessful() {
        assertDoesNotThrow(() -> this.processorController.delete(1L));
        
        ResponseEntity<Void> deleteResponse = this.processorController.delete(1L);

        assertNotNull(deleteResponse);
        assertEquals(deleteResponse.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("replace returns no content when successful")
    void replace_ReturnsNoContent_WhenSuccessful() {
        assertDoesNotThrow(() -> this.processorController.replace(ProcessorPutRequestBodyCreator.createProcessorRequestBody()));

        ResponseEntity<Void> replaceResponse = this.processorController.replace(ProcessorPutRequestBodyCreator.createProcessorRequestBody());

        assertNotNull(replaceResponse);
        assertEquals(replaceResponse.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}