package lab.spboot.microservices.anagrafemicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name="lra",url = "http://localhost:8080")

public interface LRAClient {
    @PostMapping("/saga/initiate")
   String initiateTx(Map payloadMap) throws Exception;
    //String startTx(String workflowName);


    @PostMapping("/saga/register")
    String register(String workflowSteps);
}
