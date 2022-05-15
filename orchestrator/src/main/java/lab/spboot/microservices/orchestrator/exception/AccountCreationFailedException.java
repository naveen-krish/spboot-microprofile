package lab.spboot.microservices.orchestrator.exception;

public class AccountCreationFailedException extends Exception{
    public AccountCreationFailedException(String message){
        super(message);
    }
}
