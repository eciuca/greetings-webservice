package com.emanuelciuca.training.rest;

import com.emanuelciuca.training.model.Greeting;
import com.emanuelciuca.training.service.GreetingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingsResource {

    private final GreetingsService service;

    @Autowired
    public GreetingsResource(GreetingsService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                             @RequestParam(value = "lang", defaultValue = "en") String language) {
        return service.createNewGreeting(name, language);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreetingById(@PathVariable(value = "id") Long id) {
        var greetingById = service.getById(id);

        return ResponseEntity.of(greetingById);
    }

    @GetMapping()
    public List<Greeting> getAllGreetings() {
        return service.getAllGreetings();
    }

}
