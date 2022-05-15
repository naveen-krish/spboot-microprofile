package lab.spboot.microservices.orchestrator.domain;

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

    @Override
    public String toString() {
        return "{id:"+ getId()+",name:" + getName() + ",address:" + getAddress()
                + ",account:" + getAccount() + "}";
    }
}
