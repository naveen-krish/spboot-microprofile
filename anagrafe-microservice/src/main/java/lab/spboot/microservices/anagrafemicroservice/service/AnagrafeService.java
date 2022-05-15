package lab.spboot.microservices.anagrafemicroservice.service;

import lab.spboot.microservices.anagrafemicroservice.domain.Customer;
import lab.spboot.microservices.anagrafemicroservice.repository.AnagrafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagrafeService {

   // private Map<Integer, String> customerMap;
    @Autowired
    AnagrafeRepository repository;

//    @PostConstruct
//    private void init() {
//        this.customerMap = new HashMap<Integer, String>();
////        customerMap.put(1,"userX");
////        customerMap.put(2,"userY");
//    }

    public Customer addCustomer(Customer customer) {
        return repository.save(customer);
    }

    public void removeCustomer(String id){

        try{
            Thread.sleep(10000);
        }catch(Exception e){

        }
        repository.deleteById(Integer.valueOf(id));
    }
}
