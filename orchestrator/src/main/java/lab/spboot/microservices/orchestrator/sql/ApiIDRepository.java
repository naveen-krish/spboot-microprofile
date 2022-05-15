package lab.spboot.microservices.orchestrator.sql;

import lab.spboot.microservices.orchestrator.domain.ApiRequest;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ApiIDRepository extends CrudRepository<ApiRequest, Integer> {

}
