package it.sella.microservices.util;

import java.util.UUID;

public class IdempotencyIDGenerator {

    public static String generateTxId(){
        return  UUID.randomUUID().toString();
    }
}
