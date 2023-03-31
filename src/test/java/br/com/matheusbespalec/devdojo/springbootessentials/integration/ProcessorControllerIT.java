package br.com.matheusbespalec.devdojo.springbootessentials.integration;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.repository.ProcessorRepository;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorCreator;
import br.com.matheusbespalec.devdojo.springbootessentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProcessorControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProcessorRepository processorRepository;

    @Test
    @DisplayName("list returns page of processors when successful")
    void list_ReturnsPageOfProcessors_WhenSuccessful() {
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());

        ResponseEntity<PageableResponse<Processor>> processorsPage = restTemplate.exchange(
                "/processors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Processor>>() {
        });

        Assertions.assertThat(processorsPage.getBody()).isNotNull();
        Assertions.assertThat(processorsPage.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(processorsPage.getBody().toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(processorsPage.getBody().toList().get(0)).isEqualTo(processorSaved);
    }

    @Test
    @DisplayName("list returns empty page when processors is not found")
    void list_ReturnsEmptyPage_WhenProcessorsIsNotFound() {
        ResponseEntity<PageableResponse<Processor>> processorsPage = restTemplate.exchange(
                "/processors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Processor>>() {
                });

        Assertions.assertThat(processorsPage.getBody()).isNotNull();
        Assertions.assertThat(processorsPage.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(processorsPage.getBody().toList())
                .isEmpty();
    }

    @Test
    @DisplayName("list returns list of processors when successful")
    void listAll_ReturnsListOfProcessors_WhenSuccessful() {
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());

        ResponseEntity<List<Processor>> processorsPage = restTemplate.exchange(
                "/processors/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Processor>>() {
                });

        Assertions.assertThat(processorsPage.getBody()).isNotNull();
        Assertions.assertThat(processorsPage.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(processorsPage.getBody())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(processorsPage.getBody().get(0)).isEqualTo(processorSaved);
    }

    @Test
    @DisplayName("list returns empty list when processors is not found")
    void listAll_ReturnsEmptyList_WhenProcessorsIsNotFound() {
        ResponseEntity<List<Processor>> processorsPage = restTemplate.exchange(
                "/processors/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Processor>>() {
                });

        Assertions.assertThat(processorsPage.getBody()).isNotNull();
        Assertions.assertThat(processorsPage.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(processorsPage.getBody()).isEmpty();
    }
}
