package it.sella.microservices.delegate;

import it.sella.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AddressCompensation implements SagaCompensation {

    private final Logger LOGGER = LoggerFactory.getLogger(AddressCompensation.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = ProcessConstants.ADDRESS_ROLLBACK_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            Integer address_entity_id = Integer.valueOf((String) execution.getVariable(ProcessConstants.ADDRESS_ENTITY));
           // System.out.println(" Address Id -> " + address_entity_id);
          //  Thread.sleep(10000);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, address_entity_id, String.class);
            System.out.println("*** Executing Address Compensation -> " +address_entity_id+ " status -> " + result.getStatusCode());

        } catch (Exception e) {
            System.out.println(" Anagrafe Compensation Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}