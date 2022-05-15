package lab.spboot.microservices.orchestrator.domain;

import lab.spboot.microservices.orchestrator.saga.enums.CustomerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerInfoResponse {

    private int id;
    private String name;
    private String address;
    private String account;
    private CustomerStatus status;
}
