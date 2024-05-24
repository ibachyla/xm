package com.github.ibachyla.xm.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the API module.
 */
@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@ConditionalOnProperty(name = "xm.api.enabled", havingValue = "true")
public class ApiConfiguration {

  /**
   * Creates a base request specification for all API requests.
   *
   * @param apiProperties API properties
   * @return base request specification
   */
  @Bean
  public RequestSpecification reqSpec(ApiProperties apiProperties) {
    RestAssuredConfig config = RestAssured.config()
        .httpClient(HttpClientConfig.httpClientConfig()
            .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000)
            .setParam(CoreConnectionPNames.SO_TIMEOUT, 5000));

    return new RequestSpecBuilder()
        .setConfig(config)
        .setBaseUri(apiProperties.baseUrl())
        // I'd prefer to use filters
        // (https://github.com/rest-assured/rest-assured/wiki/Usage#filters) for better integration
        // with the main logger, but that should be enough for a small task.
        .log(LogDetail.ALL)
        .build();
  }
}
