package com.soma.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    //@Retry(name="sample-api", fallbackMethod = "harcodedResponse")

    @CircuitBreaker(name="default", fallbackMethod = "harcodedResponse")
    public String sampleApi() {
        logger.info("Sample API call receive");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
        return forEntity.getBody();
    }

    public String harcodedResponse(Exception ex){
        return "fallback-response";
    }
}
