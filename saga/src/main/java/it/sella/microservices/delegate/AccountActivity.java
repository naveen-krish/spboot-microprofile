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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountActivity implements SagaActivity {

    private final Logger LOG = Logger.getLogger(AccountActivity.class.getName());


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
            LOG.log(Level.INFO,"[ {0} ] INVOKING TX-ENDPOINT -> {1} ", new Object[]{"AnagrafeSaga",getMicroServiceName(ProcessConstants.ACCOUNT_CREATE_SERVICE_URL)});
         //   Thread.sleep(10000);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
            execution.setVariable(ProcessConstants.ACCOUNT_ENTITY, result.getBody());
        //    System.out.println("*** Executing Account Activity EntityId -> " + result.getBody());
        } catch (Exception e) {
            LOG.log(Level.INFO, " [ {0} ] SERVICE DOWN TRANSACTION FAILED ON API...  -> TX_SERVICE_HOST_URL/{1} - RETRY PROCESS WILL BE EXECUTED...", new Object[]{"AnagrafeSaga", getMicroServiceName(ProcessConstants.ACCOUNT_CREATE_SERVICE_URL)});

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
                System.out.println(" Account Activity Error -> " + e.getMessage());
                throw new Exception(e.getMessage());
            }
        }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public  String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }
    }

