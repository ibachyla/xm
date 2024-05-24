package com.github.ibachyla.xm.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URI;
import java.util.List;

/**
 * Represents a film in the Star Wars universe.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Film(String title, List<URI> planets, List<URI> starships, List<URI> characters) {
}
