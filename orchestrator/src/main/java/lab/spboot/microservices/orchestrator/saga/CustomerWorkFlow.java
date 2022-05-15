package lab.spboot.microservices.orchestrator.saga;

import java.util.List;

public class CustomerWorkFlow implements Workflow{

    private final List<WorkflowStep> steps;

    public CustomerWorkFlow(List<WorkflowStep> steps){
        this.steps = steps;
    }
    @Override
    public List<WorkflowStep> getSteps() {
        return null;
    }
}
