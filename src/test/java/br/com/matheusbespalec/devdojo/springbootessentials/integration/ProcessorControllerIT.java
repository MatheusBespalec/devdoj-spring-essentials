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
import org.springframework.http.HttpEntity;
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
    @DisplayName("listAll returns list of processors when successful")
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
    @DisplayName("listAll returns empty list when processors is not found")
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

    @Test
    @DisplayName("findById returns processor when successful")
    void findById_ReturnsProcessor_WhenSuccessful() {
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());
        ResponseEntity<Processor> response = restTemplate.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody()).isEqualTo(processorSaved);
    }

    @Test
    @DisplayName("findById throws BadRequestException when processor is not found")
    void findById_ThrowsBadRequestException_WhenProcessorIsNotFound() {
        ResponseEntity<Processor> response = restTemplate.getForEntity("/processors/{id}", Processor.class, 1);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("save persist processor when successful")
    void save_PersistsProcessor_WhenSuccessful() {
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();

        ResponseEntity<Processor> response = restTemplate.postForEntity("/processors", processorToBeSaved, Processor.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        Assertions.assertThat(response.getBody().getName()).isEqualTo(processorToBeSaved.getName());
        Assertions.assertThat(response.getBody().getBaseClock()).isEqualTo(processorToBeSaved.getBaseClock());
        Assertions.assertThat(response.getBody().getCores()).isEqualTo(processorToBeSaved.getCores());
        Assertions.assertThat(response.getBody().getThreads()).isEqualTo(processorToBeSaved.getThreads());
    }

    @Test
    @DisplayName("replace updates processor when successful")
    void replace_UpdatesProcessor_WhenSuccessful() {
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        ResponseEntity<Processor> response = restTemplate.postForEntity("/processors", processorToBeSaved, Processor.class);
        Processor processorSaved = response.getBody();

        processorSaved.setCores(12);

        ResponseEntity<Void> replaceResponse = restTemplate.exchange("/processors", HttpMethod.PUT, new HttpEntity<>(processorSaved), Void.class);
        Assertions.assertThat(replaceResponse).isNotNull();
        Assertions.assertThat(replaceResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Processor> responseFindById = restTemplate.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());
        Assertions.assertThat(responseFindById.getBody().getName()).isEqualTo(processorToBeSaved.getName());
        Assertions.assertThat(responseFindById.getBody().getBaseClock()).isEqualTo(processorToBeSaved.getBaseClock());
        Assertions.assertThat(responseFindById.getBody().getThreads()).isEqualTo(processorToBeSaved.getThreads());

        Assertions.assertThat(responseFindById.getBody().getId()).isEqualTo(processorSaved.getId());
        Assertions.assertThat(responseFindById.getBody().getCores()).isEqualTo(processorSaved.getCores());
    }

    @Test
    @DisplayName("delete removes processor when successful")
    void delete_RemovesProcessor_WhenSuccessful() {
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        ResponseEntity<Processor> saveResponse = restTemplate.postForEntity("/processors", processorToBeSaved, Processor.class);
        Processor processorSaved = saveResponse.getBody();

        restTemplate.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());

        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/processors/{id}", HttpMethod.DELETE, new HttpEntity<>(processorSaved), Void.class, processorSaved.getId());
        Assertions.assertThat(deleteResponse).isNotNull();
        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Processor> responseFindById = restTemplate.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());
        Assertions.assertThat(responseFindById.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
