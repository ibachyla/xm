package com.github.ibachyla.xm.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the API module.
 *
 * @param enabled Enables or disables configuration for the API tests
 * @param baseUrl Base URL that will be used for all API requests
 */
@Validated
@ConfigurationProperties(prefix = "xm.api")
public record ApiProperties(
    boolean enabled,

    @NotBlank
    String baseUrl
) {
}
