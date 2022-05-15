package lab.spboot.microservices.anagrafemicroservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Customer")
@Table(name = "customer_info")
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;

    @Override
    public String toString() {
    System.out.println("Inside Customer.toString()");
        return "{firstName:" + this.getFirstName() + ",lastName:" + this.getLastName()+ "}";
    }
}