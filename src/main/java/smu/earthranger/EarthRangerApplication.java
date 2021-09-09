package smu.earthranger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EarthRangerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarthRangerApplication.class, args);
	}

}
