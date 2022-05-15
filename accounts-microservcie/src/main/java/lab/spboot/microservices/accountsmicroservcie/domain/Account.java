package lab.spboot.microservices.accountsmicroservcie.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Account")
@Table(name = "account_info")
@Getter
@Setter
@NoArgsConstructor
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int account;
    private double accountBalance;

    @Override
    public String toString() {

        return "{address1:" + this.getAccount() + ",address2:" + this.getAccountBalance()+ "}";
    }
}
