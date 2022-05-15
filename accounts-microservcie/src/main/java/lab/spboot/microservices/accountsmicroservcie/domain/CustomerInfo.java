package lab.spboot.microservices.accountsmicroservcie.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerInfo {
    private int id;
    private String account;
}
