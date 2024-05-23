package com.github.ibachyla.xm.api;

import com.github.ibachyla.xm.api.actions.SwApiActions;
import com.github.ibachyla.xm.api.models.Film;
import com.github.ibachyla.xm.api.models.Person;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
@SpringBootTest
public class TestTaskTwo {

    @Autowired
    SwApiActions swApiActions;

    @Test
    void verifyStarshipPresence() {
        String personName = "Vader";

        Optional<Person> person = swApiActions.searchFor(personName);
        assertThat(person).as("Check if person with name %s is found", personName).isPresent();

        Optional<Film> film = person.get().films()
                .stream()
                .map(uri -> swApiActions.get(uri, Film.class))
                .min(Comparator.comparingInt(f -> f.planets().size()));
        assertThat(film).as("Check that film with the least number of planets is found").isPresent();

        assertThat(film.get().starships())
                .as("Check that film contains %s starships", person.get().name())
                .containsAll(person.get().starships());
    }

    @Test
    void findTheOldestPerson() {

    }
}
