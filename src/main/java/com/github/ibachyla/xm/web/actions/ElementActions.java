package com.github.ibachyla.xm.web.actions;

import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Lazy
@Component
@RequiredArgsConstructor
public class ElementActions {

    private final WebDriverProvider webDriverProvider;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(1000);

    public boolean isVisible(WebElement element) {
        return isVisible(element, DEFAULT_TIMEOUT);
    }

    public boolean isVisible(WebElement element, Duration timeout) {
        try {
            new WebDriverWait(driver(), timeout).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isVisible(By locator) {
        return find(locator).map(this::isVisible).orElse(false);
    }

    public void click(WebElement element) {
        scrollIntoView(element);

        try {
            new WebDriverWait(driver(), DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            return;
        }

        element.click();
    }

    public void click(By locator) {
        find(locator).ifPresent(this::click);
    }

    public void scrollIntoView(WebElement element) {
        if (!isVisible(element)) return;

        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({block: \"center\", inline: \"nearest\"});", element);
    }

    public Optional<WebElement> find(By locator) {
        try {
            return Optional.of(new WebDriverWait(driver(), DEFAULT_TIMEOUT)
                    .until(ExpectedConditions.presenceOfElementLocated(locator)));
        } catch (TimeoutException e) {
            return Optional.empty();
        }
    }

    private WebDriver driver() {
        return webDriverProvider.get();
    }
}
