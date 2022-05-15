package lab.spboot.microservices.db.service;

import lab.spboot.microservices.db.ApiRepository;
import lab.spboot.microservices.db.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Service
public class SchedulerService {

    @Autowired
    private ApiRepository apiRepository;

    @Scheduled(fixedDelay = 15000)
    public void runJob() throws InterruptedException {

        try {
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(" Scheduler running  ...");
            for (Iterator<ApiRequest> iterator = apiRepository.findAllSaga().iterator(); iterator.hasNext(); ) {
                ApiRequest apiRequest = iterator.next();
                System.out.println(" APIRequest values " + apiRequest.getApiRequest());
                ResponseEntity<String> result = restTemplate.postForEntity(apiRequest.getWorkFlowUrl(), apiRequest.getApiRequest(), String.class);
                System.out.println(" Scheduler Service Post Result " + result.toString());
            }

        }catch (Throwable e) {
            System.out.println(" Scheduler Error "+e.getMessage());
        }
    }
}
