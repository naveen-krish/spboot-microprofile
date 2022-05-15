package lab.spboot.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/anagrafe/**")
							.uri("http://localhost:8081/"))
							.build();
				/*.route(p -> p
						.path("/anagrafe/**")
						.filters(f -> f.rewritePath("/anagrafe/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						.uri("lb://anagrafe")).build();*/
	}
}
