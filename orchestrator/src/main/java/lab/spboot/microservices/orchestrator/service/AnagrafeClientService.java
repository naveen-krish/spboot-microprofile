package lab.spboot.microservices.orchestrator.service;

import io.github.resilience4j.retry.annotation.Retry;
import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.exception.AddressCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.CustomerCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.CustomerReversalFailedException;
import lab.spboot.microservices.orchestrator.feign.AnagrafeFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnagrafeClientService {
    @Autowired
    AnagrafeFeignClient anagrafeFeignClient;

    Logger logger = LoggerFactory.getLogger(AnagrafeClientService.class);


    @Retry(name = "retryCustomer",fallbackMethod="customerFallback")
    public Map invokeAnagrafeMS(CustomerInfo customerInfo) throws AddressCreationFailedException {
        Map accMap = null;
        logger.info("Anagrafe Customer Create executed "  + " at " + new Date());
        accMap = anagrafeFeignClient.createCustomer(customerInfo);
        return accMap;
    }

    public Map customerFallback(Throwable t) throws CustomerCreationFailedException {

        if(t.getMessage().contains("Connection refused"))
            throw new CustomerCreationFailedException("Customer Creation Failed...");
        System.out.println(" Anagrafe Customer Create Fallback executed ..."+t.getMessage());
        return new HashMap();
    }

    @Retry(name = "retryReversalCustomer",fallbackMethod="customerReversalFallback")
    public Map invokeDeleteAnagrafeMS(CustomerInfo customerInfo) throws CustomerReversalFailedException {
        Map accMap = null;
        logger.info("Anagrafe Customer Reversal executed "  + " at " + new Date());
        accMap = anagrafeFeignClient.deleteCustomer(customerInfo);
        return accMap;
    }

    public Map customerReversalFallback(Throwable t) throws CustomerReversalFailedException {

        if(t.getMessage().contains("Connection refused"))
            throw new CustomerReversalFailedException("Customer Reversal Failed...");
        System.out.println(" Anagrafe Customer Reversal  Fallback executed ..."+t.getMessage());
        return new HashMap();
    }
}
