package lab.spboot.microservices.addressmicroservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Address")
@Table(name = "address_info")
@Getter
@Setter
@NoArgsConstructor
public class Address implements Serializable {

     @Id
     @GeneratedValue(strategy= GenerationType.AUTO)
     private Integer id;

     private String address1;
     private String address2;

     @Override
     public String toString() {

          return "{address1:" + this.getAddress1() + ",address2:" + this.getAddress2()+ "}";
     }
}
