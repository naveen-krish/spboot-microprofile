package lab.spboot.microservices.addressmicroservice.controller;

import lab.spboot.microservices.addressmicroservice.domain.Address;
import lab.spboot.microservices.addressmicroservice.service.AddressService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("address")
public class AddressController {

    Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressService addressService;

    @GetMapping("/address/customerAddress")
    public Address getAddress() {
        logger.info(" << getAddress () invoked >> ");
        return new Address();
    }

    @PostMapping(value ="/createAddress" ,produces = "application/json")
    public String createAddress(@RequestHeader("x-idempotency-id") String txId,@RequestBody Address address){
        System.out.println(" Creating Address in Address System for TX-ID "+txId);

        Address addressEntity = addressService.addAddress(address);
        int id = addressEntity.getId();
        JSONObject entity = new JSONObject();
        entity.put("entity_id", String.valueOf(id));
        return entity.toString();
    }

    @PutMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress(@RequestBody String id){

        addressService.removeAddress(id);
        return ResponseEntity.ok(String.valueOf(id));
    }
}
