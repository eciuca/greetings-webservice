package com.emanuelciuca.training.repo;

import com.emanuelciuca.training.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GreetingsRepository extends JpaRepository<Long, Greeting> {

    Optional<Greeting> findByContent(String content);
}
