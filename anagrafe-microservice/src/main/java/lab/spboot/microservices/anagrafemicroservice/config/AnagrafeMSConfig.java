package lab.spboot.microservices.anagrafemicroservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "anagrafe")
@Getter
@Setter
@ToString
public class AnagrafeMSConfig {

    private String msg;
    private String version;
    private String url;
    private List<String> causale;
}
