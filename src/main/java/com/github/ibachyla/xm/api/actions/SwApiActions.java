package com.github.ibachyla.xm.api.actions;

import com.github.ibachyla.xm.api.models.Person;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;

@Lazy
@Component
@RequiredArgsConstructor
public class SwApiActions {

    private static final String PEOPLE_ENDPOINT = "/people";

    private static final String SEARCH_QUERY_PARAM = "search";

    private final RequestSpecification reqSpec;

    private final Raw raw = new Raw();

    public Raw raw() {
        return raw;
    }

    public <T> T get(URI uri, Class<T> clazz) {
        return raw.get(uri)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(clazz);
    }

    public Optional<Person> searchFor(String name) {
        return raw.searchFor(name)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("results", Person.class)
                .stream()
                .findFirst();
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

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final class Raw {

        public Response get(URI uri) {
            return log(given().get(uri));
        }

        public Response searchFor(String name) {
            return log(given()
                    .queryParam(SEARCH_QUERY_PARAM, name)
                    .get(PEOPLE_ENDPOINT));
        }
    }
}
