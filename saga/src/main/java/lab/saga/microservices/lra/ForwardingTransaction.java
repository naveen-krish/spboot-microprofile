package lab.saga.microservices.lra;

public interface ForwardingTransaction {

    void invokeTx(String payload) throws Exception;
}
