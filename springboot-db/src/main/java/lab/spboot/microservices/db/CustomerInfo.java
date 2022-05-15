package lab.spboot.microservices.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        return "{name:" + this.getName() + ",address:" + this.getAddress()
                + ",account:" + this.getAccount() + "}";
    }
}
