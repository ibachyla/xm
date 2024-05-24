package com.github.ibachyla.xm.web.driver;

import com.github.ibachyla.xm.web.WebUiProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link WebDriverProvider} that uses {@link WebDriverManager} for creating
 * and quitting WebDriver instances.
 */
@Component
@Lazy
@RequiredArgsConstructor
public class WebDriverProviderImpl implements WebDriverProvider {

  private final WebUiProperties webUiProperties;

  private WebDriver driver;

  @Override
  public WebDriver get() {
    if (driver != null) {
      return driver;
    }

    driver = WebDriverManager.getInstance(resolveManagerType()).create();
    resize();

    return driver;
  }

  private DriverManagerType resolveManagerType() {
    return DriverManagerType.valueOf(webUiProperties.browser().toUpperCase());
  }

  private void resize() {
    driver.manage().window()
        .setSize(new Dimension(webUiProperties.width(), webUiProperties.height()));
  }
}
