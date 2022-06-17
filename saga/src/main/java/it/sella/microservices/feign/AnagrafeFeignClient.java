package it.sella.microservices.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name="anagrafe",url = "http://localhost:8081")
public interface AnagrafeFeignClient {

    @PostMapping("/anagrafe/createCustomer")
    String createCustomer(String customerInfo);

//    @PostMapping("/anagrafe/deleteCustomer")
//    Map deleteCustomer(@RequestBody CustomerInfo customerInfo);
}