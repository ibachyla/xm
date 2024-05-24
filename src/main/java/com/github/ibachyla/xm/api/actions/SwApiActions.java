package com.github.ibachyla.xm.api.actions;

import com.github.ibachyla.xm.api.models.Film;
import com.github.ibachyla.xm.api.models.Person;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Provides methods for interacting with the Star Wars API.
 */
@Lazy
@Component
@RequiredArgsConstructor
public class SwApiActions {

  private static final String PEOPLE_ENDPOINT = "/people";
  private static final String FILMS_ENDPOINT = "/films";

  private static final String SEARCH_QUERY_PARAM = "search";

  private final RequestSpecification reqSpec;

  private final Raw raw = new Raw();

  /**
   * Get raw API actions.
   * <p>These methods do not perform any assertions or deserialization on the response and return it
   * as is.</p>
   *
   * @return raw API actions
   */
  public Raw raw() {
    return raw;
  }

  /**
   * Perform a GET request to the specified URI and deserialize the response to the specified class.
   * <p>Response is expected to have 200 status code. Otherwise {@link AssertionError} will be
   * thrown.</p>
   *
   * @param uri   URI
   * @param clazz class to deserialize the response to
   * @param <T>   type of the response
   * @return deserialized response
   */
  public <T> T get(URI uri, Class<T> clazz) {
    return raw.get(uri)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .as(clazz);
  }

  /**
   * Search for a person by name. If multiple people are found, the first one will be returned.
   * <p>Response is expected to have 200 status code. Otherwise {@link AssertionError} will be
   * thrown.</p>
   *
   * @param name name of the person
   * @return person
   */
  public Optional<Person> searchForPerson(String name) {
    return raw.searchForPerson(name)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .jsonPath()
        .getList("results", Person.class)
        .stream()
        .findFirst();
  }

  /**
   * Get all films.
   * <p>Response is expected to have 200 status code. Otherwise {@link AssertionError} will be
   * thrown.</p>
   *
   * @return list of films
   */
  public List<Film> getAllFilms() {
    return raw.getAllFilms()
        .then()
        .statusCode(HttpStatus.SC_OK)
        .extract()
        .jsonPath()
        .getList("results", Film.class);
  }

  private RequestSpecification given() {
    return RestAssured.given().spec(reqSpec);
  }

  private Response log(Response response) {
    return response.then()
        .log()
        .all()
        .extract()
        .response();
  }

  /**
   * Raw API actions.
   * <p>Contains methods that do not perform any assertions or deserialization on the response and
   * return it as is.</p>
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public final class Raw {

    /**
     * Perform a GET request to the specified URI.
     *
     * @param uri URI
     * @return response
     */
    public Response get(URI uri) {
      return log(given().get(uri));
    }

    /**
     * Get a person by ID.
     *
     * @param id ID of the person
     * @return response
     */
    public Response getPerson(int id) {
      return log(given()
          .basePath(PEOPLE_ENDPOINT)
          .get("/" + id));
    }

    /**
     * Search for a person by name.
     *
     * @param name name of the person
     * @return response
     */
    public Response searchForPerson(String name) {
      return log(given()
          .queryParam(SEARCH_QUERY_PARAM, name)
          .basePath(PEOPLE_ENDPOINT)
          .get());
    }

    /**
     * Get all films.
     *
     * @return response
     */
    public Response getAllFilms() {
      return log(given()
          .basePath(FILMS_ENDPOINT)
          .get());
    }
  }
}
