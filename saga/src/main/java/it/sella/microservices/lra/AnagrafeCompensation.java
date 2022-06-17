package it.sella.microservices.lra;

import it.sella.microservices.constants.ProcessConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class AnagrafeCompensation implements CompensatingTransaction {

    private final Logger LOGGER = LoggerFactory.getLogger(AnagrafeCompensation.class);

    @Override
    public void rollbackTx(String payload,Long id) throws Exception {
        System.out.println("*** Executing Anagrafe Compensation ***");
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = ProcessConstants.ANAGRAFE_ROLLBACK_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
         //   Integer anagrafe_entity_id = Integer.valueOf((String) execution.getVariable(ProcessConstants.ANAGRAFE_ENTITY));
            ResponseEntity<String> result = restTemplate.postForEntity(uri,id, String.class);
            System.out.println("*** Executing Anagrafe Compensation -> " +id+ " status -> " + result.getStatusCode());

        } catch (Exception e) {
            System.out.println(" Anagrafe Compensation Error -> " + e.getMessage());
           // throw new Exception(e.getMessage());
        }
    }
}