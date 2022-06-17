package it.sella.microservices.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public interface SagaActivity extends JavaDelegate {

    void invokeTx(String payload) throws Exception;

}
