package com.github.ibachyla.xm.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.ibachyla.xm.api.actions.SwApiActions;
import com.github.ibachyla.xm.api.models.Film;
import com.github.ibachyla.xm.api.models.Person;
import groovy.util.logging.Slf4j;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Use cases implementation for the second task.
 */
@Slf4j
@Tag("api")
@SpringBootTest
public class TestTaskTwo {

  private static final Logger log = LoggerFactory.getLogger(TestTaskTwo.class);
  @Autowired
  SwApiActions swApiActions;

  /**
   * Use cases 1-3.
   */
  @Test
  void verifyStarshipPresence() {
    String personName = "Vader";

    Optional<Person> person = swApiActions.searchForPerson(personName);
    assertThat(person).as("Check if person with name %s is found", personName).isPresent();

    Optional<Film> film = person.get().films()
        .stream()
        .map(uri -> swApiActions.get(uri, Film.class))
        .min(Comparator.comparingInt(f -> f.planets().size()));
    assertThat(film).as("Check that film with the least number of planets is found")
        .isPresent();

    assertThat(film.get().starships())
        .as("Check that film contains %s starships", person.get().name())
        .containsAll(person.get().starships());
  }

  /**
   * Use case 4.
   */
  @Test
  void findTheOldestPerson() {
    List<Film> films = swApiActions.getAllFilms();

    List<URI> charactersAppearingInAllFilms = films.stream()
        .map(Film::characters)
        .reduce((characters1, characters2) -> {
          characters1.retainAll(characters2);
          return characters1;
        })
        .orElseThrow();

    assertThat(charactersAppearingInAllFilms)
        .as("Number of characters appearing in all films should be from 1 to 8")
        .hasSizeBetween(1, 8);

    Optional<Person> oldestPerson = charactersAppearingInAllFilms.stream()
        .map(uri -> swApiActions.get(uri, Person.class))
        .min(Comparator.comparing(Person::birthYear));

    assertThat(oldestPerson).as("Check that the oldest person is found").isPresent();

    log.info("The oldest person is {}. Birth year {}", oldestPerson.get().name(),
        oldestPerson.get().birthYear());
  }

  /**
   * Use case 5.
   */
  @Test
  void verifyPeopleEndpointAgainstSchema() {
    int personId = 1;

    swApiActions.raw()
        .getPerson(personId)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .assertThat()
        .body(matchesJsonSchemaInClasspath("api/schemas/people.json"));
  }
}
