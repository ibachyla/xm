package com.github.ibachyla.xm.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the web UI module.
 *
 * @param enabled  Enables or disables configuration for the web UI tests
 * @param browser  The browser to use for the web UI tests
 * @param width    The width of the browser window
 * @param height   The height of the browser window
 * @param homepage The URL of the homepage
 */
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
