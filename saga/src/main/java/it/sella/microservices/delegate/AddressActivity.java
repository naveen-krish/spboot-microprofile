package it.sella.microservices.delegate;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import it.sella.microservices.constants.ProcessConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressActivity implements SagaActivity {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(AddressActivity.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //  Map customerMap = (Map) execution.getVariable(ProcessConstants.VAR_NAME_customerId);
        LOG.log(Level.INFO,"[ {0} ] INVOKING TX-ENDPOINT -> {1} ", new Object[]{"AnagrafeSaga",getMicroServiceName(ProcessConstants.ADDRESS_CREATE_SERVICE_URL)});

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
            LOG.log(Level.INFO, " [ {0} ] TRANSACTION API RESPONSE ->  [ {1} ] ", new Object[]{"AnagrafeSaga", result.getBody()});

            execution.setVariable(ProcessConstants.ADDRESS_ENTITY, result.getBody());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


    @Override
    public void invokeTx(String payload) throws Exception {

      try{
          OkHttpClient client = new OkHttpClient();
          com.squareup.okhttp.MediaType JSON = com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
          RequestBody body = RequestBody.create(JSON, payload);
          Request request = new Request.Builder()
                  .url(ProcessConstants.ADDRESS_CREATE_SERVICE_URL)
                  .post(body)
                  .build();
          Response response = client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(" Address Activity Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public  String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }
}