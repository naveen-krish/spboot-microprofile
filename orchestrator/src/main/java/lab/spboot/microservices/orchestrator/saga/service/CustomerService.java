package lab.spboot.microservices.orchestrator.saga.service;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.saga.dto.OrchestratorRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);
//    @Autowired
//    private FluxSink<OrchestratorRequestDTO> sink;
//
//    public void createCustomer(CustomerInfo customerInfo) {
//        logger.info("Customer Service",sink.toString());
//        this.sink.next(this.getOrchestratorRequestDTO(customerInfo));
//
//    }

    public OrchestratorRequestDTO getOrchestratorRequestDTO(CustomerInfo customerInfo) {
        OrchestratorRequestDTO requestDTO = new OrchestratorRequestDTO();
        requestDTO.setId(customerInfo.getId());
        requestDTO.setAddress(customerInfo.getAddress());
        requestDTO.setName(customerInfo.getName());
        requestDTO.setAccount(customerInfo.getAccount());
        return requestDTO;
    }
}
