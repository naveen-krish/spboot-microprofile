package lab.spboot.microservices.anagrafemicroservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerInfo {
    private int id;
    private String name;
    private String address;
    private String account;

}
