package lab.saga.microservices.delegate;

import lab.saga.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressCompensation implements SagaCompensation {

    private final Logger LOG = Logger.getLogger(AddressCompensation.class.getName());
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        LOG.log(Level.INFO,"EXECUTING COMPENSATION TRANSACTION SAGA -> {0} ", "AnagrafeSaga");
        final String baseUrl = ProcessConstants.ADDRESS_ROLLBACK_SERVICE_URL;

        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(String.valueOf(execution.getVariable(ProcessConstants.ADDRESS_ENTITY)));
            String address_entity_id = String.valueOf(json.get("entity_id"));
            LOG.log(Level.INFO,"[ {0} ] INVOKING COMPENSATION-ENDPOINT -> {1} -> {2} ", new Object[]{"AnagrafeSaga",getMicroServiceName(ProcessConstants.ADDRESS_ROLLBACK_SERVICE_URL),address_entity_id});

            //  Thread.sleep(10000);
            restTemplate.put(ProcessConstants.ADDRESS_ROLLBACK_SERVICE_URL, new Object[]{address_entity_id});

            //     ResponseEntity<String> result = restTemplate.postForEntity(uri, address_entity_id, String.class);
         //   System.out.println("*** Executing Address Compensation -> " + address_entity_id + " status -> ");

     }catch( Exception e){
            //   System.out.println(" Anagrafe Compensation Error -> " + e.getMessage());
            LOG.log(Level.INFO, " [ {0} ] FAILED TO EXECUTE COMPENSATION API...  -> TX_SERVICE_HOST_URL/{1} - MARKING FOR RECONCILIATION...", new Object[]{"AnagrafeSaga", getMicroServiceName(ProcessConstants.ADDRESS_ROLLBACK_SERVICE_URL)});
            jmsTemplate.convertAndSend("compensationQueue", "FailedTxPayload");
            throw new Exception(e.getMessage());
        }
    }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public  String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }

}