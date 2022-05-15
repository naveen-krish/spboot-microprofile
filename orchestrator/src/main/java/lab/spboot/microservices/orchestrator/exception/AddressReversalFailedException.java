package lab.spboot.microservices.orchestrator.exception;

public class AddressReversalFailedException extends Exception{
    public AddressReversalFailedException(String message){
        super(message);
    }
}