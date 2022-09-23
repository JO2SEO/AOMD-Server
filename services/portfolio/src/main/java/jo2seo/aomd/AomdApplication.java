package jo2seo.aomd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@OpenAPIDefinition
@SpringBootApplication
public class AomdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AomdApplication.class, args);
	}

}
