package com.emanuelciuca.workshops.docker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GREETINGS")
public class Greeting {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "content")
    private String content;

    public static Greeting from(String content) {
        Greeting greeting = new Greeting();
        greeting.content = content;

        return greeting;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeting greeting = (Greeting) o;
        return id == greeting.id &&
                content.equals(greeting.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}
