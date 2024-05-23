package com.github.ibachyla.xm.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URI;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(String name, List<URI> films, List<URI> starships) {
}
