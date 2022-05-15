package lab.spboot.microservices.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApiRepository extends CrudRepository<ApiRequest, Integer> {

    @Query(value = "SELECT * from microservices_db.api_request u where u.status = 'KO'",nativeQuery=true)
    public List<ApiRequest> findAllSaga();

   // @Query(value = "select api_request->\"$.id\"  from api_request where JSON_EXTRACT(api_request,\"$.id\") = ?1",nativeQuery=true)
    @Query(value = "SELECT CASE WHEN COUNT(api_request->\"$.id\") > 0 THEN true ELSE false END FROM api_request  where JSON_EXTRACT(api_request,\"$.id\") = ?1",nativeQuery=true)
   // @Query(value="select new java.lang.Boolean(count(api_request->\"$.id\") > 0) FROM api_request  where JSON_EXTRACT(api_request,\"$.id\") = ?1",nativeQuery=true)
     public Integer isRecordExists(Integer id);
}
