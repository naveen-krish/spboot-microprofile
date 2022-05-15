package lab.spboot.microservices.addressmicroservice.controller;

import lab.spboot.microservices.addressmicroservice.domain.Address;
import lab.spboot.microservices.addressmicroservice.service.AddressService;
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

    @PostMapping("/createAddress")
    public ResponseEntity<String> createAddress(@RequestBody Address address){

        Address addressEntity = addressService.addAddress(address);
        int id = addressEntity.getId();
        logger.info(" << Address Controller >> ",id);
          return ResponseEntity.ok(String.valueOf(id));
    }

    @PostMapping("/deleteAddress")
    public void deleteAddress(@RequestBody String id){
        addressService.removeAddress(id);
    }
}
