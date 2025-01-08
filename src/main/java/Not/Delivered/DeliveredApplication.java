package Not.Delivered;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeliveredApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveredApplication.class, args);
	}

}
