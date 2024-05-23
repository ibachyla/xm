package com.github.ibachyla.xm.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.net.URI;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Film(String title, List<URI> planets, List<URI> starships) {
}
