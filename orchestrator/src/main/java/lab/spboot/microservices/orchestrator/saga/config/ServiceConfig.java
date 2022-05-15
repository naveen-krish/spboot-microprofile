package lab.spboot.microservices.orchestrator.saga.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    Logger logger = LoggerFactory.getLogger(ServiceConfig.class);
//    @Bean
//    public DirectProcessor<OrchestratorRequestDTO> publisher(){
//        logger.info(" ServiceConfig Publisher ");
//        return DirectProcessor.create();
//    }
//
//    @Bean
//    public FluxSink<OrchestratorRequestDTO> sink(DirectProcessor<OrchestratorRequestDTO> publisher){
//       logger.info(" ServiceConfig FluxSink ",publisher.log());
//        return publisher.sink();
//    }
}
