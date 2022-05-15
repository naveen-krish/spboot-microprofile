package lab.spboot.microservices.orchestrator.exception;

public class AccountsReversalFailedException extends Exception{
    public AccountsReversalFailedException(String message){
        super(message);
    }
}