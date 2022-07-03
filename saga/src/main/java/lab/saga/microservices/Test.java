package lab.saga.microservices;

import lab.saga.microservices.constants.ProcessConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {

        System.out.println(" Name -> "+getMicroServiceName(ProcessConstants.ANAGRAFE_CREATE_SERVICE_URL));
    }

    private static Pattern pattern = Pattern.compile(".*/([^/#|?]*)(#.*|$)");

    public static String getMicroServiceName(String url) {
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }

}
