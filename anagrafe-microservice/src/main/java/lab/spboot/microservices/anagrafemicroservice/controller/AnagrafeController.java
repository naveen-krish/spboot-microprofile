package lab.spboot.microservices.anagrafemicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lab.spboot.microservices.anagrafemicroservice.config.AnagrafeMSConfig;
import lab.spboot.microservices.anagrafemicroservice.domain.AnagrafeProperties;
import lab.spboot.microservices.anagrafemicroservice.domain.Customer;
import lab.spboot.microservices.anagrafemicroservice.domain.CustomerInfo;
import lab.spboot.microservices.anagrafemicroservice.repository.AnagrafeRepository;
import lab.spboot.microservices.anagrafemicroservice.service.AnagrafeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("anagrafe")
public class AnagrafeController {

    @Autowired
    AnagrafeMSConfig anagrafeMsConfig;


    @Autowired
    AnagrafeService anagrafeService;
    Logger logger = LoggerFactory.getLogger(AnagrafeController.class);
    private int attempt=1;

    @GetMapping("/anagrafe/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        AnagrafeProperties properties = new AnagrafeProperties(anagrafeMsConfig.getMsg(), anagrafeMsConfig.getVersion(),
                anagrafeMsConfig.getUrl(), anagrafeMsConfig.getCausale());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

//    @GetMapping("/anagrafe/customers")
//   // @CircuitBreaker(name="customerDetails",fallbackMethod="getAllCustomersFallback")
//    //@CircuitBreaker(name="customerDetails")
//     @Retry(name = "retryCustomerDetails",fallbackMethod="getAllCustomersFallback")
//    public List<Customer> getAllCustomers(@RequestHeader(value = "lab-sella-correlation-id",required = false) String correlationId) {
//        logger.info("retry method called "+attempt++ +" times "+" at "+new Date());
//
//        List<Customer> customerList = repository.getAllCustomers();
//
//       // logger.info(" << Customer Details API with Trace Id >> " + correlationId);
//
//        return customerList;
//    }
//
//    private List<Customer> getAllCustomersFallback(@RequestHeader(value = "lab-sella-correlation-id",required = false) String correlationId,Throwable t){
//        List<Customer> customerList = repository.getAllCustomersWithoutAddress();
//        logger.info(" **** Fallback API executed after retrying " +(attempt-1) +" times...  ***");
//        return customerList;
//    }

    @PostMapping(value="/createCustomer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer){
        logger.info(" Anagrafe Customer Payload ",customer.getLastName());

        Customer customerEntity = anagrafeService.addCustomer(customer);
        int id = customerEntity.getId();
        return ResponseEntity.ok(String.valueOf(id));
    }

    @PostMapping("/deleteCustomer")
    public void deleteCustomer(@RequestBody String id){
         anagrafeService.removeCustomer(id);
    }

}
