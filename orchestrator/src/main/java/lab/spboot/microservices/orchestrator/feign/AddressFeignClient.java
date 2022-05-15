package lab.spboot.microservices.orchestrator.feign;

import lab.spboot.microservices.orchestrator.domain.CustomerInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="address",url = "http://localhost:8083")
public interface AddressFeignClient {

    @PostMapping("/address/createAddress")
    Map createAddress(@RequestBody CustomerInfo customerInfo);

    @PostMapping("/address/deleteAddress")
    Map deleteAddress(@RequestBody CustomerInfo customerInfo);
}
