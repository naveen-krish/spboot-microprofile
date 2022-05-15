package lab.spboot.microservices.accountsmicroservcie.controller;

import lab.spboot.microservices.accountsmicroservcie.domain.Account;
import lab.spboot.microservices.accountsmicroservcie.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountsController {
    Logger logger = LoggerFactory.getLogger(AccountsController.class);
    @Autowired
    private AccountsService accountsService;

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account){
        logger.info(" << Accounts Controller >> ",account);
        return ResponseEntity.ok(String.valueOf(accountsService.addAccount(account)));
    }

    @PostMapping("/deleteAccount")
    public void deleteAccount(@RequestBody String id){
        accountsService.removeAccount(id);
    }
}
