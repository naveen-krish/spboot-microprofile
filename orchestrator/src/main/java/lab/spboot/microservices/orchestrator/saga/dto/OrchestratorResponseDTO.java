package lab.spboot.microservices.orchestrator.saga.dto;


import lab.spboot.microservices.orchestrator.saga.enums.CustomerStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Data
public class OrchestratorResponseDTO {

    private int id;
    private String name;
    private String address;
    private String account;
    private CustomerStatus status;

}
