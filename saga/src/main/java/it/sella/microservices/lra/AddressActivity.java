package it.sella.microservices.lra;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import it.sella.microservices.constants.ProcessConstants;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressActivity implements ForwardingTransaction {

    private final Logger LOG = Logger.getLogger(AddressActivity.class.getName());

    @Override
    public void invokeTx(String payload) throws Exception {

        LOG.log(Level.INFO,"[ {0} ] INVOKING TX-ENDPOINT -> {1} ", new Object[]{"AnagrafeSaga",getMicroServiceName(ProcessConstants.ADDRESS_CREATE_SERVICE_URL)});

        try{
            OkHttpClient client = new OkHttpClient();
            com.squareup.okhttp.MediaType JSON = com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, payload);
            Request request = new Request.Builder()
                    .url(ProcessConstants.ADDRESS_CREATE_SERVICE_URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            LOG.log(Level.INFO, " [ {0} ] TRANSACTION API RESPONSE ->  [ {1} ] ", new Object[]{"AnagrafeSaga", response.body()});

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public  String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }
}