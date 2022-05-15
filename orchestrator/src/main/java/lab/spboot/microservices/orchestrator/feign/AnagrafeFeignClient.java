package lab.spboot.microservices.orchestrator.feign;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="anagrafe",url = "http://localhost:8081")
public interface AnagrafeFeignClient {

    @PostMapping("/anagrafe/createCustomer")
    Map createCustomer(@RequestBody CustomerInfo customerInfo);

    @PostMapping("/anagrafe/deleteCustomer")
    Map deleteCustomer(@RequestBody CustomerInfo customerInfo);
}