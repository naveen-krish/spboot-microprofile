package it.sella.microservices.lra;


import com.squareup.okhttp.*;
import it.sella.microservices.constants.ProcessConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpEntity;

@EnableFeignClients
public class AnagrafeActivity implements ForwardingTransaction {

    private final Logger LOGGER = LoggerFactory.getLogger(AnagrafeActivity.class);
    private HttpEntity<String> entity;



    @Override
    public void invokeTx(String payload) throws Exception {

        try{
            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.MediaType JSON = com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, payload);
            Request request = new Request.Builder()
                    .url(ProcessConstants.ANAGRAFE_CREATE_SERVICE_URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            System.out.println(" Anagrafe Activity Error -> " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


}