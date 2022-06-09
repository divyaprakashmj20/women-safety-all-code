package com.tus.cloudgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackMethodController {

    @RequestMapping("/userServiceFallBack")
    public Mono<String> userServiceFallBackMethod() {
        return Mono.just("User Service is taking longer than Expected." +
                " Please try again later");
    }

    @RequestMapping("/sosServiceFallBack")
    public Mono<String> sosServiceFallBackMethod() {
        return Mono.just("SOS Service is taking longer than Expected." +
                " Please try again later");
    }
}
