package lab.spboot.microservices.orchestrator.saga.service;


import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.saga.*;
import lab.spboot.microservices.orchestrator.saga.dto.OrchestratorRequestDTO;
import lab.spboot.microservices.orchestrator.saga.dto.OrchestratorResponseDTO;
import lab.spboot.microservices.orchestrator.saga.enums.CustomerStatus;
import lab.spboot.microservices.orchestrator.saga.steps.CustomerStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrchestratorService {

    Logger logger = LoggerFactory.getLogger(OrchestratorResponseDTO.class);
    @Autowired
    @Qualifier("customer")
    private WebClient customerClient;



    public Mono<OrchestratorResponseDTO> createCustomer(final OrchestratorRequestDTO requestDTO){
        logger.info(" Orchestrator service ",requestDTO.getName());
        Workflow customerWorkflow = this.getCustomerWorkflow(requestDTO);
        return Flux.fromStream(() -> customerWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean)
                        synchronousSink.next(true);
                    else
                        synchronousSink.error(new WorkflowException("create customer failed!"));
                }))
                .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, CustomerStatus.CUSTOMER_CREATE_COMPLETED)))
                .onErrorResume(ex -> this.revertCustomer(customerWorkflow, requestDTO));

    }

    private Mono<OrchestratorResponseDTO> revertCustomer(final Workflow workflow, final OrchestratorRequestDTO requestDTO){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDTO, CustomerStatus.CUSTOMER_CANCELLED)));
    }

    private Workflow getCustomerWorkflow(OrchestratorRequestDTO requestDTO){
        WorkflowStep customerStep = new CustomerStep(this.customerClient, this.getCustomerDTO(requestDTO));
     //   WorkflowStep inventoryStep = new InventoryStep(this.inventoryClient, this.getInventoryRequestDTO(requestDTO));
        return new CustomerWorkFlow(List.of(customerStep));
    }

    private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, CustomerStatus status){
        OrchestratorResponseDTO customerInfo = new OrchestratorResponseDTO();
        customerInfo.setId(requestDTO.getId());
        customerInfo.setName(requestDTO.getName());
        customerInfo.setAddress(requestDTO.getAddress());
        customerInfo.setAccount(requestDTO.getAccount());
        customerInfo.setStatus(status);
        return customerInfo;
    }

    private CustomerInfo getCustomerDTO(OrchestratorRequestDTO requestDTO){
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId(requestDTO.getId());
        customerInfo.setName(requestDTO.getName());
        customerInfo.setAddress(requestDTO.getAddress());
        customerInfo.setAccount(requestDTO.getAccount());
        return customerInfo;
    }


//    private InventoryRequestDTO getInventoryRequestDTO(OrchestratorRequestDTO requestDTO){
//        InventoryRequestDTO inventoryRequestDTO = new InventoryRequestDTO();
//        inventoryRequestDTO.setUserId(requestDTO.getUserId());
//        inventoryRequestDTO.setProductId(requestDTO.getProductId());
//        inventoryRequestDTO.setOrderId(requestDTO.getOrderId());
//        return inventoryRequestDTO;
//    }

}
