package it.sella.microservices.delegate;

import it.sella.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AccountCompensation implements SagaCompensation {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountCompensation.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("<<< Executing Account Compensation >>> ");
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = ProcessConstants.ACCOUNT_ROLLBACK_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //  customerMap.forEach((k, v) -> System.out.println(" Account Rollback Delegate Key: " + k + ": Value: " + v));
        try {
            Integer account_entity_id = Integer.valueOf((String) execution.getVariable(ProcessConstants.ACCOUNT_ENTITY));
            System.out.println(" Account Id -> " + account_entity_id);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, account_entity_id, String.class);
            System.out.println("*** Executing Account Compensation -> " +account_entity_id+ " status -> " + result.getStatusCode());

        } catch (Exception e) {
         //   System.out.println(" Account Compensation Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}