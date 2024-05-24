package com.github.ibachyla.xm.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ibachyla.xm.web.models.Stock;
import com.github.ibachyla.xm.web.pages.HomePage;
import com.github.ibachyla.xm.web.pages.StockPage;
import com.github.ibachyla.xm.web.pages.StockPageFactory;
import com.github.ibachyla.xm.web.pages.StocksPage;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Use cases implementation for the first task.
 */
@Tag("web")
@SpringBootTest
@ExtendWith(SoftAssertionsExtension.class)
public class TestTaskOne {

  @InjectSoftAssertions
  private SoftAssertions softly;

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

    softly.assertThat(stockPage.getMinMarginPercentage())
        .as("Check that minimum margin percentage is correct")
        .isEqualTo(stock.get().minMarginPercentage());
    softly.assertThat(stockPage.getSpread())
        .as("Check that spread is correct")
        .isEqualTo(stock.get().spread());
    softly.assertThat(stockPage.getSymbol())
        .as("Check that symbol is correct")
        .isEqualTo(stock.get().symbol());
    softly.assertThat(stockPage.getTradeSize())
        .as("Check that trade size is correct")
        .isEqualTo(stock.get().tradeSize());
    softly.assertThat(stockPage.getDescription())
        .as("Check that description is correct")
        .isSubstringOf(stock.get().description());
    softly.assertThat(stockPage.getLongSwap())
        .as("Check that long swap is correct")
        .isEqualTo(stock.get().longSwap());
    softly.assertThat(stockPage.getShortSwap())
        .as("Check that short swap is correct")
        .isEqualTo(stock.get().shortSwap());
    softly.assertThat(stockPage.getLimitAndStopLevels())
        .as("Check that limit and stop levels are correct")
        .isEqualTo(stock.get().limitAndStopLevels());
  }
}
