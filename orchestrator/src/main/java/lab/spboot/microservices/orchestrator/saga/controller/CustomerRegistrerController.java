package lab.spboot.microservices.orchestrator.saga.controller;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.saga.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerRegistrerController {

    Logger logger = LoggerFactory.getLogger(CustomerRegistrerController.class);
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public void createOrder(@RequestBody CustomerInfo requestDTO){
        logger.info(" Customer CreateController ",requestDTO.getName());
        // this.customerService.createCustomer(requestDTO);
    }
}
