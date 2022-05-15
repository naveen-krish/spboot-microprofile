package it.sella.microservices.delegate;

import it.sella.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AddressActivity implements SagaActivity {

    private final Logger LOGGER = LoggerFactory.getLogger(AddressActivity.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //  Map customerMap = (Map) execution.getVariable(ProcessConstants.VAR_NAME_customerId);
        String payload = (String) execution.getVariable(ProcessConstants.CUSTOMER_SAGA_NAME);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = null;
        JSONObject json = null;
        final String baseUrl = ProcessConstants.ADDRESS_CREATE_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // customerMap.forEach((k, v) -> System.out.println(" Address Onboard Delegate Key: " + k + ": Value: " + v));
        try {
            json = new JSONObject(payload);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<String>(json.toString(), headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
            System.out.println("*** Executing Address Activity EntityId -> " + result.getBody());

            execution.setVariable(ProcessConstants.ADDRESS_ENTITY, result.getBody());
        } catch (Exception e) {
            System.out.println(" Address Activity Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }

    }
}