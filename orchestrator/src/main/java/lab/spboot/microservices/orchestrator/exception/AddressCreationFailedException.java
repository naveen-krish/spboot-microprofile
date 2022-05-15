package lab.spboot.microservices.orchestrator.exception;

public class AddressCreationFailedException extends Exception{
    public AddressCreationFailedException(String message){
        super(message);
    }
}
