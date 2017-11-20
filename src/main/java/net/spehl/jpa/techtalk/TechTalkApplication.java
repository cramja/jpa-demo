package net.spehl.jpa.techtalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TechTalkApplication {

	@Configuration
	@EnableJpaRepositories
	@EntityScan("net.spehl.jpa.techtalk.model")
	public static class AppConfig { }

	public static void main(String[] args) {
		SpringApplication.run(TechTalkApplication.class, args);
	}
}
