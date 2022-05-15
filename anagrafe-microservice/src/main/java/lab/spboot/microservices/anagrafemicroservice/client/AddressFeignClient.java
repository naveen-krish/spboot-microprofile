package lab.spboot.microservices.anagrafemicroservice.client;

import lab.spboot.microservices.anagrafemicroservice.domain.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="address",url = "http://localhost:8083")
public interface AddressFeignClient {

    @GetMapping("/address/customerAddress")
    Address getAddress();
}
