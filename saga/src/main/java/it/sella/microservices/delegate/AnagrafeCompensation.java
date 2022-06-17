package it.sella.microservices.delegate;

import it.sella.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.json.JSONObject;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnagrafeCompensation implements SagaCompensation {

    private final Logger LOG = Logger.getLogger(AnagrafeCompensation.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {


        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = ProcessConstants.ANAGRAFE_ROLLBACK_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(String.valueOf(execution.getVariable(ProcessConstants.ANAGRAFE_ENTITY)));
            String anagrafe_entity_id = String.valueOf(json.get("entity_id"));
            LOG.log(Level.INFO,"[ {0} ] INVOKING COMPENSATION-ENDPOINT -> {1} -> {2} ", new Object[]{"AnagrafeSaga",getMicroServiceName(ProcessConstants.ANAGRAFE_ROLLBACK_SERVICE_URL),anagrafe_entity_id});

            restTemplate.put(ProcessConstants.ANAGRAFE_ROLLBACK_SERVICE_URL, new Object[]{anagrafe_entity_id});
         //   LOG.log(Level.INFO, " [ {0} ] COMPENSATION API RESPONSE ->  [ {1} ] ", new Object[]{"AnagrafeSaga", result.getBody()});

        } catch (Exception e) {
          //  System.out.println(" Anagrafe Compensation Error -> " + e.getMessage());
           // throw new Exception(e.getMessage());
        }
    }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public  String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }

}