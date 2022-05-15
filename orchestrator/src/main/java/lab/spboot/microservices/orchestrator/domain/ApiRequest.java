package lab.spboot.microservices.orchestrator.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ApiRequest")
@Table(name = "api_request_data")
public class ApiRequest implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String apiRequest;

    private String workFlowUrl;
    private String status;

}
