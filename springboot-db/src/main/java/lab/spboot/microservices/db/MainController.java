package lab.spboot.microservices.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ApiRepository apiRepository;


	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}

	@PostMapping(path="/onboardWorkFlowFailed") // Map ONLY POST Requests
	public ResponseEntity<String>  saveJson (@RequestBody String payload)throws Exception {



//		Integer requestId = apiRepository.isRecordExists(customerInfo.getId());
//		System.out.println(" Is Id exists "+requestId);

		//if(requestId.intValue() == 0){
			ApiRequest apiRequest = new ApiRequest();
			JSONObject json = new JSONObject(payload);
			apiRequest.setApiRequest(json.toString());
			apiRequest.setWorkFlowUrl("http://localhost:8090/saga/onboard-workflow");
			apiRequest.setStatus("KO");
			ApiRequest entity = apiRepository.save(apiRequest);
	//	}
		return ResponseEntity.ok(String.valueOf(entity.getId()));

		//apiRepository.save(apiRequest);
	}
}
