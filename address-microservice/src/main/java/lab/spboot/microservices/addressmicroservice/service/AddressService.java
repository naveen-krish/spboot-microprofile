package lab.spboot.microservices.addressmicroservice.service;

import lab.spboot.microservices.addressmicroservice.domain.Address;
import lab.spboot.microservices.addressmicroservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

//    private Map<Integer, String> addressMap;
//    @PostConstruct
//    private void init() {
//        this.addressMap = new HashMap<Integer, String>();
////        addressMap.put(1,"USA");
////        addressMap.put(2,"UK");
//    }
//
    public Address addAddress(Address address){
       return addressRepository.save(address);
    }

    public void removeAddress(String id) {

         System.out.println(" Rollbacking Address object "+id);
         addressRepository.deleteById(Integer.valueOf(id));

    }
}
