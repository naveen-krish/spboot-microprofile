package lab.spboot.microservices.anagrafemicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lab.spboot.microservices.anagrafemicroservice.client.LRAClient;
import lab.spboot.microservices.anagrafemicroservice.config.AnagrafeMSConfig;
import lab.spboot.microservices.anagrafemicroservice.domain.AnagrafeProperties;
import lab.spboot.microservices.anagrafemicroservice.domain.Customer;
import lab.spboot.microservices.anagrafemicroservice.service.AnagrafeService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("anagrafe")
public class AnagrafeController {

    @Autowired
    AnagrafeMSConfig anagrafeMsConfig;

    @Autowired
    LRAClient lracClient;

    @Autowired
    AnagrafeService anagrafeService;
    Logger logger = LoggerFactory.getLogger(AnagrafeController.class);
    private int attempt = 1;

    @GetMapping("/anagrafe/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        AnagrafeProperties properties = new AnagrafeProperties(anagrafeMsConfig.getMsg(), anagrafeMsConfig.getVersion(),
                anagrafeMsConfig.getUrl(), anagrafeMsConfig.getCausale());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @GetMapping("/lra")
    public void lra() {

        try {
            String workflow = "{\n" +
                    "  \"sagaName\": \"AnagrafeSaga\",\n" +
                    "  \"saga\":[\n" +
                    "    {\n" +
                    //  "      \"activityName\": \"createCustomer\",\n" +
                    //   "      \"compensationActivityName\": \"rollbackCustomer\",\n" +
                    "      \"AnagrafeActivityClass\": \"it.sella.microservices.lra.AnagrafeActivity\",\n" +
                    "      \"AnagrafeCompensationClass\": \"it.sella.microservices.lra.AnagrafeCompensation\"\n" +
                    "    },\n" +
                    "   {\n" +
//            "      \"activityName\": \"createAddress\",\n" +
//            "      \"compensationActivityName\": \"rollbackAddress\",\n" +
                    "      \"AddressActivityClass\": \"it.sella.microservices.lra.AddressActivity\",\n" +
                    "      \"AddressCompensationClass\": \"it.sella.microservices.lra.AddressCompensation\"\n" +
                    "    },\n" +
                    "    {\n" +
//            "      \"activityName\": \"createAccount\",\n" +
//            "      \"compensationActivityName\": \"rollbackAccount\",\n" +
                    "      \"AccountActivityClass\": \"it.sella.microservices.lra.AccountActivity\",\n" +
                    "      \"AccountCompensationClass\": \"it.sella.microservices.lra.AccountCompensation\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";
            String jsonPayload = "{\n" +
                    "\t\n" +
                    "\t\"firstName\":\"Naveen\",\n" +
                    "\t\"lastName\":\"Krishnan\",\n" +
                    "\t\"address1\":\"India\",\n" +
                    "\t\"address2\":\"velachery\",\n" +
                    "\t\"account\":\"009\",\n" +
                    "\t\"accountBalance\":\"9999\"\n" +
                    "}";

        /*
            Need to refactor using Query Param through Feign client
         */
            //   System.out.println(" workflow Json -> " + lracClient.register(workflow));
            Map<String, Object> txPayloadMap = new HashMap<String, Object>();
            txPayloadMap.put("Saga_Name", "AnagrafeSaga");
            txPayloadMap.put("Tx_payload", jsonPayload);
            lracClient.initiateTx(txPayloadMap);
        }catch (Exception e){
            System.out.println(" Anagrafe Error -> "+e.getMessage());
        }

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

//    @PostMapping(value = "/createCustomer") //, consumes = "application/json", produces = "application/json")
//    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
//        logger.info(" Anagrafe Customer Payload ", customer.getLastName());
//
//        Customer customerEntity = anagrafeService.addCustomer(customer);
//        int id = customerEntity.getId();
//        return ResponseEntity.ok(String.valueOf(id));
//    }

    @PostMapping(value = "/createCustomer", produces = "application/json")
    public String createCustomer(@RequestBody Customer customer)  {
        System.out.println(" Anagrafe Customer Payload "+ customer.getFirstName());

        Customer customerEntity = anagrafeService.addCustomer(customer);
       int id = customerEntity.getId();
        JSONObject entity = new JSONObject();
        entity.put("entity_id", String.valueOf(id));
        return entity.toString();
    }

    @PutMapping(value="/deleteCustomer", produces = "application/json")
    public ResponseEntity<String> deleteCustomer(@RequestBody String id) {
        System.out.println(" Anagrafe Deleting Customer Entity ->"+id);
        anagrafeService.removeCustomer(id);
       return ResponseEntity.ok(String.valueOf(id));
    }

}
