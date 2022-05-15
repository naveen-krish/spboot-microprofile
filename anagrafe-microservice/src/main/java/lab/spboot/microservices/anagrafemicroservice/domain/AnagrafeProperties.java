package lab.spboot.microservices.anagrafemicroservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnagrafeProperties {

    private String msg;
    private String version;
    private List<String> causale;
    private String url;

    public AnagrafeProperties(String msg, String version,String url, List<String> causale) {
        this.msg = msg;
        this.version = version;
        this.url = url;
        this.causale = causale;
    }
}
