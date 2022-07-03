package lab.saga.microservices.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CompensationTxReceiver {

    @JmsListener(destination = "compensationQueue", containerFactory = "myFactory")
    public void receiveMessage(String json) {
        System.out.println("Received <" + json + ">");
    }
}
