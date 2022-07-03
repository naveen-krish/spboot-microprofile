package lab.saga.microservices.workflow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParentSagaWorkFlowSteps {

    private List<SagaWorkFlowSteps> sagaWorkFlowSteps;

}
