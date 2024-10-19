package com.hmg.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {

    @GetMapping("/sayHelloPublic")
    public String sayHello(){
        return "Hello from API HMG";
    }

    @GetMapping("/sayHelloProtected")
    public String sayHelloProtected(){
        return "Hello from API HMG protected";
    }
}
