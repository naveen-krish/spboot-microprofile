package it.sella.microservices.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SagaWorkFlowSteps implements Serializable {

//    @JsonProperty("saga")
    private String saga="";
    @JsonProperty("activityName")
    private String activityName;
    @JsonProperty("compensationActivityName")
    private String compensationActivityName;
    @JsonProperty("activityClass")
    private String activityClass;
    @JsonProperty("compensationClass")
    private String compensationClass;

    @Override
    public String toString() {

        return "{sagaName:" + this.getSaga() + ", activityName:" + this.getActivityName() +
                ",compensationActivityName:" + this.getCompensationActivityName() + "}";
    }
}
