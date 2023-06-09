package br.com.matheusbespalec.devdojo.springbootessentials.repository;

import br.com.matheusbespalec.devdojo.springbootessentials.domain.Processor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessorRepository extends JpaRepository<Processor, Long> {
}
