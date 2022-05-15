package lab.spboot.microservices.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Entity(name = "ApiRequest")
@Table(name = "api_request")
@Getter
@Setter
@NoArgsConstructor
public class ApiRequest implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String apiRequest;

    private String workFlowUrl;
//    private String addressWorkFlowUrl;
//    private String accountWorkFlowUrl;
    private String status="KO";

//    @Override
//    public String toString() {
//        ObjectMapper mapper = new ObjectMapper();
//        CustomerInfo customerInfo = null;
//        try {
//             customerInfo = mapper.readValue(this.getApiRequest(),CustomerInfo.class);
//             System.out.println(" API Request toString() "+customerInfo.getName());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//    }
}
