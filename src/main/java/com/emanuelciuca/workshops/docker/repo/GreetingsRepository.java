package com.emanuelciuca.workshops.docker.repo;

import com.emanuelciuca.workshops.docker.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GreetingsRepository extends JpaRepository<Greeting, Long> {

    Optional<Greeting> findByContent(String content);
}
