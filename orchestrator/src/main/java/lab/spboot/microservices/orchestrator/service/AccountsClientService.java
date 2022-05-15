package lab.spboot.microservices.orchestrator.service;

import io.github.resilience4j.retry.annotation.Retry;
import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.exception.AccountCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.AccountsReversalFailedException;
import lab.spboot.microservices.orchestrator.feign.AccountsFeignClient;
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
public class AccountsClientService {

    @Autowired
    AccountsFeignClient accountsFeignClient;
    private CustomerInfo customerInfo;

    Logger logger = LoggerFactory.getLogger(AccountsClientService.class);

    @Retry(name = "retryAccounts",fallbackMethod="accountFallback")
    public Map invokeAccountMS(CustomerInfo customerInfo) throws AccountCreationFailedException {
        Map accMap = null;
        logger.info("Accounts Create Executed "  + " at " + new Date());
          accMap = accountsFeignClient.createAccount(customerInfo);
        return accMap;
    }

    public Map accountFallback(Throwable t) throws AccountCreationFailedException {

        RestTemplate restTemplate = new RestTemplate();

        if(t.getMessage().contains("Connection refused")) {
            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:9090/demo/onboardWorkFlowFailed", customerInfo.toString(), String.class);
            System.out.println(" Accounts  Fallback executed ..." + t.getMessage());
            throw new AccountCreationFailedException("Account Creation Failed...");
          }
        return new HashMap();
    }

    @Retry(name = "retryDeleteAccounts",fallbackMethod="accountDeleteFallback")
    public Map invokeDeleteAccountMS(CustomerInfo customerInfo) throws AccountsReversalFailedException {
        Map accMap = null;
        logger.info("Accounts Reversal executed ..." + " at " + new Date());
        accMap = accountsFeignClient.deleteAccount(customerInfo);
        return accMap;
    }

    public Map accountDeleteFallback(Throwable t) throws AccountsReversalFailedException {

        if(t.getMessage().contains("Connection refused"))
            throw new AccountsReversalFailedException("Account Reversal Fallback executed...");
        System.out.println(" Accounts Reversal Fallback executed ..."+t.getMessage());
        return new HashMap();
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
