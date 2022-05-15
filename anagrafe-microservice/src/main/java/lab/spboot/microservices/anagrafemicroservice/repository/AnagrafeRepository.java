package lab.spboot.microservices.anagrafemicroservice.repository;

import lab.spboot.microservices.anagrafemicroservice.client.AddressFeignClient;
import lab.spboot.microservices.anagrafemicroservice.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Repository
public interface AnagrafeRepository extends CrudRepository<Customer, Integer> {


    Logger logger = LoggerFactory.getLogger(AnagrafeRepository.class);

//    @Autowired
//    AddressFeignClient addressFeignClient;
//
//    public List<Customer> getAllCustomers() {
//        Customer customer = null;
//        try {
//           customer = new Customer("Tony", "Stark", addressFeignClient.getAddress().getCustomerAddress());
//        }catch (Exception e) {
//            logger.info(" << Feign Error >> "+e.getMessage());
//           // e.printStackTrace();
//        }
//        return List.of(customer);
//    }
//
//    public List<Customer> getAllCustomersWithoutAddress() {
//        return List.of(new Customer("Tony", "Stark",""));
//    }
}
