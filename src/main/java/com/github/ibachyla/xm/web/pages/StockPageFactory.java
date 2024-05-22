package com.github.ibachyla.xm.web.pages;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import com.github.ibachyla.xm.web.models.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
@RequiredArgsConstructor
public class StockPageFactory {

    private final WebUiProperties webUiProperties;
    private final WebDriverProvider driverProvider;
    private final ElementActions elementActions;

    public StockPage create(Stock stock) {
        return new StockPage(stock, webUiProperties, driverProvider, elementActions);
    }
}
