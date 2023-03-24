package br.com.matheusbespalec.devdojo.springbootessentials.client;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Processor> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/processors/{id}", Processor.class,  1);
        log.info(responseEntity);

        ResponseEntity<Processor[]> processorsArray = new RestTemplate()
                .getForEntity("http://localhost:8080/processors/all", Processor[].class);
        log.info(Arrays.toString(processorsArray.getBody()));

        ResponseEntity<List<Processor>> processorsList = new RestTemplate().exchange(
                "http://localhost:8080/processors/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Processor>>() {
                }
        );
        log.info(processorsList);

        Processor ryzen9 = Processor.builder()
                .name("Ryzen 9 7950X")
                .baseClock(4.5)
                .cores(15)
                .threads(32)
                .build();

//        ResponseEntity<Processor> processorSaved = new RestTemplate()
//                .postForEntity("http://localhost:8080/processors", ryzen9, Processor.class);
        ResponseEntity<Processor> processorSaved = new RestTemplate()
                .exchange("http://localhost:8080/processors", HttpMethod.POST, new HttpEntity<>(ryzen9), Processor.class);
        log.info(processorSaved);

        Processor processorToBeUpdated = processorSaved.getBody();
        processorToBeUpdated.setCores(16);

        ResponseEntity<Void> processorUpdated = new RestTemplate()
                .exchange("http://localhost:8080/processors", HttpMethod.PUT, new HttpEntity<>(processorToBeUpdated), Void.class);
        log.info(processorUpdated);

        ResponseEntity<Void> processorDeleted = new RestTemplate()
                .exchange("http://localhost:8080/processors/{id}", HttpMethod.DELETE, null, Void.class, processorToBeUpdated.getId());
        log.info(processorDeleted);
    }
}
