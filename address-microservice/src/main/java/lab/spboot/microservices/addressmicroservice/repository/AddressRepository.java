package lab.spboot.microservices.addressmicroservice.repository;

import lab.spboot.microservices.addressmicroservice.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {
}
