package lab.saga.microservices.lra;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lab.saga.microservices.constants.ProcessConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
