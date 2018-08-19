package net.spehl.jpa.techtalk;

import net.spehl.jpa.techtalk.repo.TransactionRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories
@EntityScan("net.spehl.jpa.techtalk.model")
public class DataConfiguration {

    @Bean
    public ReadRunner readController(TransactionRepository repository) {
        return new ReadRunner(repository);
    }

    @Bean
    public WriteRunner writeController(TransactionRepository repository) {
        return new WriteRunner(repository);
    }
}
