package com.github.ibachyla.xm.web;

import com.github.ibachyla.xm.web.models.Stock;
import com.github.ibachyla.xm.web.pages.HomePage;
import com.github.ibachyla.xm.web.pages.StockPage;
import com.github.ibachyla.xm.web.pages.StockPageFactory;
import com.github.ibachyla.xm.web.pages.StocksPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("web")
@SpringBootTest
public class TestTaskOne {

    @Autowired
    HomePage homePage;

    @Autowired
    StocksPage stocksPage;

    @Autowired
    StockPageFactory stockPageFactory;

    @BeforeEach
    void setUp() {
        homePage.get();
        homePage.dismissPrivacyPopupIfVisible();
    }

    @Test
    void verifyTradingConditions() {
        homePage.navigateToStocks();
        stocksPage.isLoaded();
        stocksPage.filterStocksByCountry("Norway");

        String country = "Norway";
        assertThat(stocksPage.isCountryFilterSelected(country))
                .as("Check that %s filter is selected", country)
                .isTrue();

        String symbol = "Orkla";
        Optional<Stock> stock = stocksPage.findStock(symbol);
        assertThat(stock)
                .as("Check that %s stock is present", symbol)
                .isPresent();

        stocksPage.readMore(symbol);

        StockPage stockPage = stockPageFactory.create(stock.get());
        stockPage.isLoaded();
    }
}
