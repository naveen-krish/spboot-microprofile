package it.sella.microservices.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Customer implements Serializable {
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        System.out.println("Inside Customer.toString()");
        return "{firstName:" + this.getFirstName() + ",lastName:" + this.getLastName()+ "}";
    }

}