package it.sella.microservices.lra;

public interface ForwardingTransaction {

    void invokeTx(String payload) throws Exception;
}
