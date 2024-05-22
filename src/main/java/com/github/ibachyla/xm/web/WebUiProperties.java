package com.github.ibachyla.xm.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "xm.web-ui")
public record WebUiProperties(
        boolean enabled,

        @NotBlank
        String browser,

        @Positive
        int width,

        @Positive
        int height,

        @NotBlank
        String homepage
) {
}
