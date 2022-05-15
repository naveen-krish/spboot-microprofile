package lab.spboot.microservices.orchestrator.exception;

public class CustomerReversalFailedException extends Exception{
    public CustomerReversalFailedException(String message){
        super(message);
    }
}