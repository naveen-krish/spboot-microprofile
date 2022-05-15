package lab.spboot.microservices.anagrafemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"lab.spboot.microservices.anagrafemicroservice"})

public class AnagrafeMicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(AnagrafeMicroserviceApplication.class, args);
	}

}
