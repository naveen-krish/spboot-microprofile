package lab.saga.microservices.controller;

import lab.saga.microservices.constants.ProcessConstants;
import lab.saga.microservices.service.SagaTransactionManager;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/saga")
public class SagaOrchestatorController {


    @Autowired
    SagaTransactionManager sagaTransactionManager;

    @PostMapping("/onboard-workflow")
    public void onboardWorkflow(@RequestBody String payload) {

        //System.out.println(" Saga Orchestrator " + payload);
        initiateCustomerWorkflow(payload);

    }

    @PostMapping("/register-saga")
    public void registerSaga(@RequestBody String payload) {

       // System.out.println(" Saga Orchestrator " + payload);
        saga(payload);

    }

    /**
     * we need a method returning the {@link ProcessInstance} to allow for easier tests,
     * that's why I separated the REST method (without return) from the actual implementaion (with return value)
     */
    public void initiateCustomerWorkflow(String jsonPayload) {

        Map<String, Object> customerMap = new HashMap<String, Object>();
        customerMap.put("customerInfo", jsonPayload);
        sagaTransactionManager.executeSagaTransaction(jsonPayload, ProcessConstants.CUSTOMER_SAGA_NAME);

    }

    public void saga(String jsonPayload) {
        sagaTransactionManager.registerSaga(jsonPayload);
    }
}
