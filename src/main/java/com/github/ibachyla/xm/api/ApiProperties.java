package com.github.ibachyla.xm.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "xm.api")
public record ApiProperties(
        boolean enabled,

        @NotBlank
        String baseUrl
) {
}
