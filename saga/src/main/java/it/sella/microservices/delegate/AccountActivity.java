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

public class AccountActivity implements SagaActivity {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountActivity.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String payload = (String) execution.getVariable(ProcessConstants.CUSTOMER_SAGA_NAME);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = null;
        JSONObject json = null;
        final String baseUrl = ProcessConstants.ACCOUNT_CREATE_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            json = new JSONObject(payload);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<String>(json.toString(), headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
            execution.setVariable(ProcessConstants.ACCOUNT_ENTITY, result.getBody());
            System.out.println("*** Executing Account Activity EntityId -> " + result.getBody());
        } catch (Exception e) {
           // System.out.println(" Account Activity Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

}
