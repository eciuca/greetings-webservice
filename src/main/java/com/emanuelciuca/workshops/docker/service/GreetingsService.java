package com.emanuelciuca.workshops.docker.service;

import com.emanuelciuca.workshops.docker.model.Greeting;
import com.emanuelciuca.workshops.docker.repo.GreetingsRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class GreetingsService {

    private static final String APP_LANG_TEMPLATES_LOCATION = "APP_LANG_TEMPLATES_LOCATION";

    private final GreetingsRepository repository;

    @Autowired
    public GreetingsService(GreetingsRepository repository) {
        this.repository = repository;
    }

    public Greeting createNewGreeting(String name, String language) {
        var content = createGreetingContent(name, language);
        var greeting = Greeting.from(content);

        return repository.save(greeting);
    }

    public Optional<Greeting> getById(long id) {
        return repository.findById(id);
    }

    public List<Greeting> getAllGreetings() {
        return repository.findAll();
    }

    private String createGreetingContent(String name, String language) {
        var langTemplate =
                readTemplateFromEnvironmentVariableLocation(language)
                        .or(readTemplateFromClasspath(language))
                        .orElseThrow(() -> new IllegalArgumentException("Could not find the template for language:  " + language));

        return String.format(langTemplate, name);
    }

    private Optional<String> readTemplateFromEnvironmentVariableLocation(String language) {
        return Optional.ofNullable(System.getenv(APP_LANG_TEMPLATES_LOCATION))
                .flatMap(folder -> readTemplateFromFile(language, folder))
                .filter(Strings::isNotEmpty);
    }

    private Optional<String> readTemplateFromFile(String language, String templatesLocation) {
        var fileLocation = templatesLocation + "/" + language + ".txt";
        var filePath = Path.of(fileLocation);

        try {
            if (!Files.exists(filePath)) {
                return Optional.empty();
            }

            var content = Files.readString(filePath);
            return Optional.of(content);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Supplier<Optional<String>> readTemplateFromClasspath(String language) {
        return () -> {
            var templateLocation = "greeting_templates/" + language + ".txt";
            var resourceAsStream = getClass().getClassLoader().getResourceAsStream(templateLocation);
            return Optional.ofNullable(resourceAsStream)
                    .flatMap(this::convertInputStreamToString)
                    .filter(Strings::isNotEmpty);
        };
    }

    private Optional<String> convertInputStreamToString(InputStream inputStream) {
        try {
            var streamAsBytes = inputStream.readAllBytes();
            var baos = new ByteArrayOutputStream(streamAsBytes.length);
            baos.write(streamAsBytes);
            var streamAsString = baos.toString();

            return Optional.of(streamAsString);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
