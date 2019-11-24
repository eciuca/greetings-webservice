package com.emanuelciuca.training.rest;

import com.emanuelciuca.training.model.Greeting;
import com.emanuelciuca.training.service.GreetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greetings")
public class GreetingsResource {

    private final GreetingsService service;

    @Autowired
    public GreetingsResource(GreetingsService service) {
        this.service = service;
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                             @RequestParam(value = "lang", defaultValue = "en") String language) {
        var greeting = service.createNewGreeting(name, language);

        return greeting;
    }


}
