package it.sella.microservices.service;


import it.sella.microservices.constants.ProcessConstants;
import it.sella.microservices.delegate.*;
import it.sella.microservices.engine.MicroServiceSagaProcessEngine;
import it.sella.microservices.saga.SagaBuilder;
import it.sella.microservices.workflow.SagaWorkFlowSteps;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SagaTransactionManager {
    @Autowired
    private ProcessEngine camunda;

    @Autowired
    MicroServiceSagaProcessEngine microServiceSagaProcessEngine;


    public void executeSagaTransaction(String payLoad, String sagaName) {

        ProcessInstanceWithVariables processInstance = camunda.getRuntimeService().createProcessInstanceByKey(
                        sagaName)

                .setVariable(sagaName, payLoad)
                .executeWithVariablesInReturn();

     //   processInstance.getVariables().forEach((k, v) -> System.out.println(" Process Status Key : " + k + ": Value: " + v));


    }

    public void registerSaga(String workflowPaylod) {

        SagaBuilder saga = microServiceSagaProcessEngine.registerSaga(workflowPaylod, ProcessConstants.CUSTOMER_SAGA_NAME);

    }

}
