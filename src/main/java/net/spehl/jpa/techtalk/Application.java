package net.spehl.jpa.techtalk;

import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Application {

	@Configuration
	@EnableJpaRepositories
	@EntityScan("net.spehl.jpa.techtalk.model")
	public static class AppConfig {

		@Bean
		public MemcachedClientFactoryBean memcachedClientFactoryBean() {
			MemcachedClientFactoryBean factoryBean = new MemcachedClientFactoryBean();
			factoryBean.setServers("localhost:11211");
			factoryBean.setProtocol(Protocol.BINARY);
			factoryBean.setTimeoutExceptionThreshold(1000);
			factoryBean.setOpTimeout(100);
			return factoryBean;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
