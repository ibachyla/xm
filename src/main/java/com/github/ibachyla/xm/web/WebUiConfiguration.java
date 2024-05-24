package com.github.ibachyla.xm.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the web UI module.
 */
@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@ConditionalOnProperty(name = "xm.web-ui.enabled", havingValue = "true")
public class WebUiConfiguration {
}
