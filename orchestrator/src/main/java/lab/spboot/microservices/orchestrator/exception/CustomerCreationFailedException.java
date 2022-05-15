package lab.spboot.microservices.orchestrator.exception;

public class CustomerCreationFailedException extends Exception{
    public CustomerCreationFailedException(String message){
        super(message);
    }
}
