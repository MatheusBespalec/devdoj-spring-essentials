package br.com.matheusbespalec.devdojo.springbootessentials.controller;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.service.ProcessorService;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorCreator;
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
}