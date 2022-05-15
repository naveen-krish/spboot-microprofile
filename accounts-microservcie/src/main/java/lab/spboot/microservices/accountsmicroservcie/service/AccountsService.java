package lab.spboot.microservices.accountsmicroservcie.service;

import lab.spboot.microservices.accountsmicroservcie.domain.Account;
import lab.spboot.microservices.accountsmicroservcie.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountsService {
    private Map<Integer, String> accountsMap;
    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    private void init() {
  //      this.accountsMap = new HashMap<Integer, String>();
//        accountsMap.put(1,"001");
//        accountsMap.put(2,"002");
    }

    public Account addAccount(Account account) {

        return accountRepository.save(account);
    }

    public void removeAccount(String id){
       accountRepository.deleteById(Integer.valueOf(id));
    }
}
