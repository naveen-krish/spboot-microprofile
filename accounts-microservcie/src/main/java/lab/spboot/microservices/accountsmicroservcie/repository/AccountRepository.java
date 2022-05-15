package lab.spboot.microservices.accountsmicroservcie.repository;

import lab.spboot.microservices.accountsmicroservcie.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
