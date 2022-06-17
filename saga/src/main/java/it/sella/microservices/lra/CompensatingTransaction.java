package it.sella.microservices.lra;

public interface CompensatingTransaction {

    void rollbackTx(String payload,Long id) throws Exception;
}
