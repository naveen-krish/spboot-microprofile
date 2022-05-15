package lab.spboot.microservices.orchestrator.service;

import io.github.resilience4j.retry.annotation.Retry;
import lab.spboot.microservices.orchestrator.domain.ApiRequest;
import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.exception.AccountCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.AddressCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.AddressReversalFailedException;
import lab.spboot.microservices.orchestrator.feign.AddressFeignClient;
import lab.spboot.microservices.orchestrator.sql.ApiIDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddressClientService {

    @Autowired
    AddressFeignClient addressFeignClient;
    @Autowired
    ApiIDRepository apiIdRepository;

    private CustomerInfo customerInfo;

    Logger logger = LoggerFactory.getLogger(AddressClientService.class);

    @Retry(name = "retryAddress",fallbackMethod="addressFallback")
    public Map invokeAddressMS() throws AddressCreationFailedException {
        Map accMap = null;
        logger.info("Address Create executed " + " at " + new Date());
        accMap = addressFeignClient.createAddress(customerInfo);
        return accMap;
    }

    public Map addressFallback(Throwable t) throws AddressCreationFailedException {

        RestTemplate restTemplate = new RestTemplate();

        if (t.getMessage().contains("Connection refused")){

            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:9090/demo/onboardWorkFlowFailed", customerInfo.toString(), String.class);
           // ApiRequest result = restTemplate.postForObject("http://localhost:9090/demo/onboardWorkFlowFailed", customerInfo.toString(), ApiRequest.class);
            System.out.println(" Address Create Fallback executed ..." );
           // apiIdRepository.save(result);
            throw new AddressCreationFailedException("Address Creation Failed...");
       }
            return new HashMap();
    }

    @Retry(name = "retryReversalAddress",fallbackMethod="addressReversalFallback")
    public Map invokeDeleteAddressMS() throws AddressReversalFailedException {
        Map accMap = null;
        logger.info("Address Reversal executed "  + " at " + new Date());
        accMap = addressFeignClient.deleteAddress(customerInfo);
        return accMap;
    }

    public Map addressReversalFallback(Throwable t) throws AddressReversalFailedException {

        RestTemplate restTemplate = new RestTemplate();

        if(t.getMessage().contains("Connection refused")) {
            System.out.println(" Address Reversal Fallback executed ..." + t.getMessage());
            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:9090/demo/onboardWorkFlowFailed", customerInfo.toString(), String.class);
            throw new AddressReversalFailedException("Address Reversal Failed...");

        }
        return new HashMap();
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
