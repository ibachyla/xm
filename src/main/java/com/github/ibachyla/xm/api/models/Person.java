package com.github.ibachyla.xm.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.List;

/**
 * Represents a person in the Star Wars universe.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(String name, @JsonProperty("birth_year") SwYear birthYear, List<URI> films,
                     List<URI> starships) {
}
