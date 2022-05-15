package lab.spboot.microservices.orchestrator.saga;

import java.util.List;

public interface Workflow {

    List<WorkflowStep> getSteps();

}