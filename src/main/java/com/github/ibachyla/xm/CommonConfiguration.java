package com.github.ibachyla.xm;

import com.github.ibachyla.xm.web.WebUiConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(WebUiConfiguration.class)
public class CommonConfiguration {
}
