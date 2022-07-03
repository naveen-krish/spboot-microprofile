package lab.saga.microservices.constants;

public interface ProcessConstants {

    public static final String PROCESS_KEY_customer = "customer";

    public static final String VAR_NAME_customerId = "customerId";
    public static final String ADDRESS_ENTITY = "ADDRESS_ENTITY";
    public static final String ACCOUNT_ENTITY =  "ACCOUNT_ENTITY";
    public static final String ANAGRAFE_ENTITY = "ANAGRAFE_ENTITY";
    public static final String VAR_NAME_customerAddress = "address";
    public static final String VAR_NAME_customerAccount = "account";

    public static String ANAGRAFE_CREATE_SERVICE_URL= "http://localhost:8081/anagrafe/createCustomer";
    public static String ANAGRAFE_ROLLBACK_SERVICE_URL= "http://localhost:8081/anagrafe/deleteCustomer";
    public static String ADDRESS_CREATE_SERVICE_URL= "http://localhost:8083/address/createAddress";
    public static String ADDRESS_ROLLBACK_SERVICE_URL= "http://localhost:8083/address/deleteAddress";
    public static String ACCOUNT_CREATE_SERVICE_URL= "http://localhost:8084/accounts/createAccount";
    public static String ACCOUNT_ROLLBACK_SERVICE_URL= "http://localhost:8084/accounts/deleteAccount";
   // public static String ANAGRAFE_CREATE_SERVICE_URL= "http://localhost:8081/anagrafe/createCustomer";
    public static String CUSTOMER_SAGA_NAME = "microservices-customer-saga";

}