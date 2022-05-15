package lab.spboot.microservices.db.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ResponseBody
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ResponseController {

    @GetMapping("/classlevel")
    public String serviceUnavailable() {
        return "The HTTP Status will be SERVICE_UNAVAILABLE (CODE 503)\n";
    }

    @GetMapping("/methodlevel")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public String ok() {
        return "Class Level HTTP Status Overriden. The HTTP Status will be OK (CODE 200)\n";
    }
}
