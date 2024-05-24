package com.github.ibachyla.xm;

import com.github.ibachyla.xm.api.ApiConfiguration;
import com.github.ibachyla.xm.web.WebUiConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Common configuration class.
 */
@SpringBootConfiguration
@Import({WebUiConfiguration.class, ApiConfiguration.class})
public class CommonConfiguration {
}
