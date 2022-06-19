package it.sella.microservices.engine;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sella.microservices.saga.SagaBuilder;
import it.sella.microservices.workflow.SagaWorkFlowSteps;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MicroServiceSagaProcessEngine {
    @Autowired
    private ProcessEngine camunda;

    public SagaBuilder registerSaga(String workflowSteps, String sagaName) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject json = new JSONObject(workflowSteps);
        JSONArray jsonarray = json.getJSONArray("saga");
        SagaBuilder saga = SagaBuilder.newSaga(sagaName);

        try {
            SagaWorkFlowSteps[] sagaWorkFlowSteps = mapper.readValue(jsonarray.toString(), SagaWorkFlowSteps[].class);

            for (SagaWorkFlowSteps sagaWorkFlowStep : sagaWorkFlowSteps) {
                sagaWorkFlowStep.setSaga(sagaName);
           //     System.out.println(" Saga Registration -> " + sagaWorkFlowStep.toString());
                saga
                        .activity(sagaWorkFlowStep.getActivityName(), Class.forName(sagaWorkFlowStep.getActivityClass()))
                        .compensationActivity(sagaWorkFlowStep.getCompensationActivityName(), Class.forName(sagaWorkFlowStep.getCompensationClass()));

            }
            saga.end().triggerCompensationOnAnyError();
             //saga.end();



//            printDom(saga.getModel().getDocument().getRootElement().getChildElements());

            camunda.getRepositoryService().createDeployment()
                    .addModelInstance("microservices-customer-saga.bpmn", saga.getModel())
                    .deploy();

        } catch (Exception e) {
            System.out.println(" Json Parse Error " + e.getMessage());
            e.printStackTrace();
        }
        return saga;
    }

    public  void printDom(List<DomElement> dom)  {

        for (DomElement element : dom) {

            // Print all elements of ArrayList
            System.out.println(" DomElement -> "+element.getLocalName());
        }

        }

}
