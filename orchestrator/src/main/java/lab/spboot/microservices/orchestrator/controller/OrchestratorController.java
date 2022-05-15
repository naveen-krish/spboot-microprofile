package lab.spboot.microservices.orchestrator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import lab.spboot.microservices.orchestrator.exception.AccountCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.AddressCreationFailedException;
import lab.spboot.microservices.orchestrator.exception.AddressReversalFailedException;
import lab.spboot.microservices.orchestrator.exception.CustomerReversalFailedException;
import lab.spboot.microservices.orchestrator.feign.AccountsFeignClient;
import lab.spboot.microservices.orchestrator.feign.AddressFeignClient;
import lab.spboot.microservices.orchestrator.feign.AnagrafeFeignClient;
import lab.spboot.microservices.orchestrator.service.AccountsClientService;
import lab.spboot.microservices.orchestrator.service.AddressClientService;
import lab.spboot.microservices.orchestrator.service.AnagrafeClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orchestrator")
public class OrchestratorController {

    @Autowired
    private AnagrafeClientService anagrafeClientService;
    @Autowired
    private AddressClientService addressClientService;
    @Autowired
    private AccountsClientService accountsClientService;
    Logger logger = LoggerFactory.getLogger(OrchestratorController.class);
    private static CustomerInfo customerStaticInfo;
    private static CustomerInfo customerStaticInfo1;
    private static List<CustomerInfo> custList = new ArrayList<CustomerInfo>();

//static{

    @PostConstruct
    public void init() {
        customerStaticInfo = new CustomerInfo();
        customerStaticInfo.setId(1);
        customerStaticInfo.setName("userX");
        customerStaticInfo.setAddress("USA");
        customerStaticInfo.setAccount("001");
        customerStaticInfo1 = new CustomerInfo();
        customerStaticInfo1.setId(2);
        customerStaticInfo1.setName("userY");
        customerStaticInfo1.setAddress("UK");
        customerStaticInfo1.setAccount("002");
        custList.add(customerStaticInfo);
        custList.add(customerStaticInfo1);
    }


    @PostMapping("/onboardCustomer")
    public ResponseEntity<String> onboardCustomer(@RequestBody CustomerInfo customerInfo) throws Exception{

//        ObjectMapper mapper = new ObjectMapper();
//        logger.info(" Onboarding Customer Transaction Payload..."+payload);
//        CustomerInfo customerInfo = mapper.readValue(payload,CustomerInfo.class);
        logger.info(" Onboarding Customer Transaction begins...",customerInfo.toString());
        addressClientService.setCustomerInfo(customerInfo);
        accountsClientService.setCustomerInfo(customerInfo);
        try {
            System.out.println(" USER DATABASE ");
            logCustomerInfo();
            System.out.println("***** Adding User Info *****");
            System.out.println();

            Map anagrafeMap = anagrafeClientService.invokeAnagrafeMS(customerInfo);
            log(anagrafeMap);
            System.out.println("***** Adding Address Info *****");
            System.out.println();
            Map addressMap = addressClientService.invokeAddressMS();
            log(anagrafeMap,addressMap);

            System.out.println("***** Adding Account Info ******");
            System.out.println();
            Map accountMap =  accountsClientService.invokeAccountMS(customerInfo);
            log(anagrafeMap,addressMap,accountMap);
            System.out.println("***** UPDATED USER DATABASE ******");
            System.out.println();
            logCustomerInfo(anagrafeMap,addressMap,accountMap);

        }catch(AccountCreationFailedException e){
            System.out.println(" Rollback Msg "+e.getMessage());
            rollbackCustomerAndAddress(customerInfo);
        }catch(AddressCreationFailedException ex){
            System.out.println(" Rollback Msg "+ex.getMessage());

            // return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service_Unavailable (CODE 201)\n");
        // throw new Exception(" Rollback Msg "+ex.getMessage());
            rollbackCustomer(customerInfo);
        }
        return new ResponseEntity<>((HttpStatus.SERVICE_UNAVAILABLE));
    }

    private void rollbackCustomerAndAddress(CustomerInfo customerInfo){
    try {
        Map anagrafeMap = anagrafeClientService.invokeDeleteAnagrafeMS(customerInfo);
        System.out.println(" Deleting User Info ");
        System.out.println();
        log(anagrafeMap);
        System.out.println(" Deleting Address Info ");
        System.out.println();
        Map addressMap = addressClientService.invokeDeleteAddressMS();
        log(anagrafeMap, addressMap);
        System.out.println("***** UPDATED USER DATABASE ******");
        System.out.println();
        logCustomerInfo();
    }catch(CustomerReversalFailedException c){
        logger.info(c.getMessage());
    }catch(AddressReversalFailedException ad){
        logger.info(ad.getMessage());
    }
    }

    private void rollbackCustomer(CustomerInfo customerInfo){
    try {
        Map anagrafeMap = anagrafeClientService.invokeDeleteAnagrafeMS(customerInfo);
        System.out.println(" Deleting User Info ");
        System.out.println();
        log(anagrafeMap);
        System.out.println();
        System.out.println("***** UPDATED USER DATABASE ******");
        System.out.println();
        logCustomerInfo();
    }catch(CustomerReversalFailedException cx){
        logger.info(cx.getMessage());
    }
    }


    private void logCustomerInfo(){
        System.out.printf("%-12s %-12s %-12s %-12s%n","ID", "UserName","Address","AccountNumber");
        System.out.printf("%-12s %-12s %-12s %-12s%n","---", "----------","-------","------------");
        for (CustomerInfo info: custList){
            System.out.printf("%-12s %-12s %-12s %-12s%n",info.getId(),info.getName(),info.getAddress(),info.getAccount());
        }
    }

    private void logCustomerInfo(Map anagrafeMap,Map addressMap,Map accMap){
        System.out.printf("%-12s %-12s %-12s %-12s%n","ID", "UserName","Address","AccountNumber");
        System.out.printf("%-12s %-12s %-12s %-12s%n","---", "----------","-------","------------");
        for (CustomerInfo info: custList){
            System.out.printf("%-12s %-12s %-12s %-12s%n",info.getId(),info.getName(),info.getAddress(),info.getAccount());
        }
        for (Object key: anagrafeMap.keySet()){
            System.out.printf("%-12s %-12s %-12s %-12s%n",key,anagrafeMap.get(key),addressMap.get(key),accMap.get(key));
        }
    }

    private void log(Map anagrafeMap){
        System.out.printf("%-12s %-12s %-12s %-12s%n","ID", "UserName","Address","AccountNumber");
        System.out.printf("%-12s %-12s %-12s %-12s%n","---", "----------","-------","------------");
        for (Object key: anagrafeMap.keySet()){
            System.out.printf("%-12s %-12s %-12s %-12s%n",key,anagrafeMap.get(key),"","");

        }
    }
    private void log(Map anagrafeMap,Map addressMap){
        System.out.printf("%-12s %-12s %-12s %-12s%n","ID", "UserName","Address","AccountNumber");
        System.out.printf("%-12s %-12s %-12s %-12s%n","---", "----------","-------","------------");

        for (Object key: anagrafeMap.keySet()){
            System.out.printf("%-12s %-12s %-12s %-12s%n",key,anagrafeMap.get(key),addressMap.get(key),"");
        }
    }

    private void log(Map anagrafeMap,Map addressMap,Map accMap){
        System.out.printf("%-12s %-12s %-12s %-12s%n","ID", "UserName","Address","AccountNumber");
        System.out.printf("%-12s %-12s %-12s %-12s%n","---", "----------","-------","------------");

        for (Object key: anagrafeMap.keySet()){
            System.out.printf("%-12s %-12s %-12s %-12s%n",key,anagrafeMap.get(key),addressMap.get(key),accMap.get(key));
        }
    }



}
