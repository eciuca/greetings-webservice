package com.emanuelciuca.training.service;

import com.emanuelciuca.training.model.Greeting;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Service
public class GreetingsService {

    private static final String DEFAULT_TEMPLATE = "Hello, %s!";
    private static final String APP_LANG_TEMPLATES_LOCATION = "APP_LANG_TEMPLATES_LOCATION";
    private final AtomicLong counter = new AtomicLong();

    public Greeting createNewGreeting(String name, String language) {
        var id = counter.incrementAndGet();
        var content = createGreetingContent(name, language);

        return new Greeting(id, content);
    }

    private String createGreetingContent(String name, String language) {
        var langTemplate =
                readTemplateFromEnvironmentVariableLocation(language)
                        .or(readTemplateFromClasspath(language))
                        .orElse(DEFAULT_TEMPLATE);

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
            String content = Files.readString(filePath);

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
