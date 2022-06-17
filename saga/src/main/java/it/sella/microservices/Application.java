package it.sella.microservices;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableAutoConfiguration
@EnableProcessApplication
@ComponentScan
//@EnableRetry
//@EnableFeignClients
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }

}