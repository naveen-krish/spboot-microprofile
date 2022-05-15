package lab.spboot.microservices.orchestrator.saga.steps;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.saga.WorkflowStep;
import lab.spboot.microservices.orchestrator.saga.WorkflowStepStatus;
import lab.spboot.microservices.orchestrator.saga.dto.OrchestratorResponseDTO;
import lab.spboot.microservices.orchestrator.saga.enums.CustomerStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class CustomerStep implements WorkflowStep {

    private final WebClient webClient;
    private CustomerInfo customerInfo;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    public CustomerStep(WebClient webClient, CustomerInfo customerInfo) {
        this.webClient = webClient;
        this.customerInfo = customerInfo;
    }

    @Override
    public WorkflowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/orchestrator/onboardCustomer")
                .body(BodyInserters.fromValue(this.customerInfo))
                .retrieve()
                .bodyToMono(OrchestratorResponseDTO.class)
                .map(r -> r.getStatus().equals(CustomerStatus.CUSTOMER_CREATED))
                .doOnNext(b -> this.stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return null;
    }
}
