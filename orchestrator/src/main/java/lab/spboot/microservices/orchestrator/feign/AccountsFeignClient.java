package lab.spboot.microservices.orchestrator.feign;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="accounts",url = "http://localhost:8084")
public interface AccountsFeignClient {

    @PostMapping("/accounts/createAccount")
    Map createAccount(@RequestBody CustomerInfo customerInfo);

    @PostMapping("/accounts/deleteAccount")
    Map deleteAccount(@RequestBody CustomerInfo customerInfo);
}