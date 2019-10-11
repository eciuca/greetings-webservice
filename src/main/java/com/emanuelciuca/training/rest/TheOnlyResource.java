package com.emanuelciuca.training.rest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TheOnlyResource {

    private static final String DEFAULT_TEMPLATE = "Hello, %s!";
    private static final String APP_LANG_TEMPLATES_LOCATION = "APP_LANG_TEMPLATES_LOCATION";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                             @RequestParam(value = "lang", defaultValue = "en") String language) {
        return new Greeting(counter.incrementAndGet(), createGreetingContent(name, language));
    }

    private String createGreetingContent(String name, String language) {
        Optional<Path> langTemplateFile = Optional.ofNullable(System.getenv(APP_LANG_TEMPLATES_LOCATION))
                .map(folder -> folder + "/" + language + ".txt")
                .map(Path::of)
                .filter(path -> path.toFile().exists());

        return langTemplateFile
                .flatMap(this::readGreeting)
                .map(fileTemplate -> String.format(fileTemplate, name))
                .orElse(String.format(DEFAULT_TEMPLATE, name));
    }

    private Optional<String> readGreeting(Path path) {
        try {
            String content = Files.readString(path);

            return Optional.of(content)
                    .filter(Strings::isNotEmpty);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
