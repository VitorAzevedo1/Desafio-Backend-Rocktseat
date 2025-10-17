package spring.boot.desafio.rocketseat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RocketseatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketseatApplication.class, args);
	}

}
