package lab.saga.microservices.service;


import lab.saga.microservices.constants.ProcessConstants;
import lab.saga.microservices.engine.MicroServiceSagaProcessEngine;
import lab.saga.microservices.saga.SagaBuilder;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SagaTransactionManager {
    @Autowired
    private ProcessEngine camunda;

    private final Logger LOG = Logger.getLogger(SagaTransactionManager.class.getName());

    @Autowired
    MicroServiceSagaProcessEngine microServiceSagaProcessEngine;


    public void executeSagaTransaction(String payLoad, String sagaName) {

        LOG.log(Level.INFO,"INITIATING TRANSACTION SAGA -> {0} ", new Object[]{sagaName});
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
