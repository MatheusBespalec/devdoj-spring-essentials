package br.com.matheusbespalec.devdojo.springbootessentials.integration;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import br.com.matheusbespalec.devdojo.springbootessentials.domain.User;
import br.com.matheusbespalec.devdojo.springbootessentials.repository.ProcessorRepository;
import br.com.matheusbespalec.devdojo.springbootessentials.repository.UserRepository;
import br.com.matheusbespalec.devdojo.springbootessentials.util.ProcessorCreator;
import br.com.matheusbespalec.devdojo.springbootessentials.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
    @Qualifier("testRestTemplateRoleUser")
    private TestRestTemplate restTemplateUser;

    @Autowired
    @Qualifier("testRestTemplateRoleAdmin")
    private TestRestTemplate restTemplateAdmin;

    @Autowired
    private ProcessorRepository processorRepository;

    @Autowired
    private UserRepository userRepository;

    private static final User USER = User.builder().username("user")
            .password("{bcrypt}$2a$10$sHKIyLUbji2/pUWmKijCzeE1nvJvq7oOvx4gR.xl7sJwjlf7WXfBe")
            .authorities("ROLE_USER")
            .build();

    private static final User ADMIN = User.builder().username("admin")
            .password("{bcrypt}$2a$10$sHKIyLUbji2/pUWmKijCzeE1nvJvq7oOvx4gR.xl7sJwjlf7WXfBe")
            .authorities("ROLE_ADMIN,ROLE_USER")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") Integer port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://127.0.0.1:"+port)
                    .basicAuthentication("user", "test");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean("testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") Integer port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://127.0.0.1:"+port)
                    .basicAuthentication("admin", "test");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list returns page of processors when successful")
    void list_ReturnsPageOfProcessors_WhenSuccessful() {
        userRepository.save(USER);
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());

        ResponseEntity<PageableResponse<Processor>> processorsPage = restTemplateUser.exchange(
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
        userRepository.save(USER);
        ResponseEntity<PageableResponse<Processor>> processorsPage = restTemplateUser.exchange(
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
        userRepository.save(USER);
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());

        ResponseEntity<List<Processor>> processorsPage = restTemplateUser.exchange(
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
        userRepository.save(USER);
        ResponseEntity<List<Processor>> processorsPage = restTemplateUser.exchange(
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
        userRepository.save(USER);
        Processor processorSaved = processorRepository.save(ProcessorCreator.createProcessorToBeSaved());
        ResponseEntity<Processor> response = restTemplateUser.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody()).isEqualTo(processorSaved);
    }

    @Test
    @DisplayName("findById throws BadRequestException when processor is not found")
    void findById_ThrowsBadRequestException_WhenProcessorIsNotFound() {
        userRepository.save(USER);
        ResponseEntity<Processor> response = restTemplateUser.getForEntity("/processors/{id}", Processor.class, 1);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("save persist processor when successful")
    void save_PersistsProcessor_WhenSuccessful() {
        userRepository.save(ADMIN);
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();

        ResponseEntity<Processor> response = restTemplateAdmin.postForEntity("/processors/admin", processorToBeSaved, Processor.class);

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
    @DisplayName("save returns 403 (Forbidden) when user authenticated is not admin")
    void save_Returns403Forbidden_WhenUserAuthenticatedIsNotAdmin() {
        userRepository.save(USER);

        ResponseEntity<Processor> response = restTemplateUser.postForEntity("/processors/admin", null, Processor.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


    @Test
    @DisplayName("replace updates processor when successful")
    void replace_UpdatesProcessor_WhenSuccessful() {
        userRepository.save(ADMIN);
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        ResponseEntity<Processor> response = restTemplateAdmin.postForEntity("/processors/admin", processorToBeSaved, Processor.class);
        Processor processorSaved = response.getBody();

        processorSaved.setCores(12);

        ResponseEntity<Void> replaceResponse = restTemplateAdmin.exchange("/processors/admin", HttpMethod.PUT, new HttpEntity<>(processorSaved), Void.class);
        Assertions.assertThat(replaceResponse).isNotNull();
        Assertions.assertThat(replaceResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Processor> responseFindById = restTemplateAdmin.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());
        Assertions.assertThat(responseFindById.getBody().getName()).isEqualTo(processorToBeSaved.getName());
        Assertions.assertThat(responseFindById.getBody().getBaseClock()).isEqualTo(processorToBeSaved.getBaseClock());
        Assertions.assertThat(responseFindById.getBody().getThreads()).isEqualTo(processorToBeSaved.getThreads());

        Assertions.assertThat(responseFindById.getBody().getId()).isEqualTo(processorSaved.getId());
        Assertions.assertThat(responseFindById.getBody().getCores()).isEqualTo(processorSaved.getCores());
    }

    @Test
    @DisplayName("replace returns 403 (Forbidden) when authenticated user is not admin")
    void replace_Returns403Forbidden_WhenUserAuthenticatedIsNotAdmin() {
        userRepository.save(USER);

        ResponseEntity<Void> replaceResponse = restTemplateUser.exchange("/processors/admin", HttpMethod.PUT, null, Void.class);

        Assertions.assertThat(replaceResponse).isNotNull();
        Assertions.assertThat(replaceResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("delete removes processor when successful")
    void delete_RemovesProcessor_WhenSuccessful() {
        userRepository.save(ADMIN);
        Processor processorToBeSaved = ProcessorCreator.createProcessorToBeSaved();
        ResponseEntity<Processor> saveResponse = restTemplateAdmin.postForEntity("/processors/admin", processorToBeSaved, Processor.class);
        Processor processorSaved = saveResponse.getBody();

        ResponseEntity<Void> deleteResponse = restTemplateAdmin.exchange("/processors/admin/{id}", HttpMethod.DELETE, new HttpEntity<>(processorSaved), Void.class, processorSaved.getId());
        Assertions.assertThat(deleteResponse).isNotNull();
        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Processor> responseFindById = restTemplateAdmin.getForEntity("/processors/{id}", Processor.class, processorSaved.getId());
        Assertions.assertThat(responseFindById.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("delete returns 403 (Forbidden when user authenticated is not admin)")
    void delete_Returns403Forbidden_WhenUserAuthenticatedIsNotAdmin() {
        userRepository.save(USER);
        ResponseEntity<Void> deleteResponse = restTemplateUser.exchange("/processors/admin/{id}", HttpMethod.DELETE, null, Void.class, 1);
        Assertions.assertThat(deleteResponse).isNotNull();
        Assertions.assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
