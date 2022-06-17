package it.sella.microservices.lra;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import it.sella.microservices.constants.ProcessConstants;
import it.sella.microservices.delegate.SagaActivity;
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

public class AccountActivity implements ForwardingTransaction {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountActivity.class);

    @Override
    public void invokeTx(String payload) throws Exception {

        try{
            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.MediaType JSON = com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, payload);
            Request request = new Request.Builder()
                    .url(ProcessConstants.ACCOUNT_CREATE_SERVICE_URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(" Account Activity Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


}
