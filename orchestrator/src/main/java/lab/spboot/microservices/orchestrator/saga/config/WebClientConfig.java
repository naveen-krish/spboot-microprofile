package lab.spboot.microservices.orchestrator.saga.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("customer")
    public WebClient paymentClient(@Value("${service.endpoints.customer}") String endpoint) {
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }
}
