package com.github.ibachyla.xm.web.pages;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import com.github.ibachyla.xm.web.elements.TopBar;
import com.github.ibachyla.xm.web.models.Stock;
import org.openqa.selenium.By;

/**
 * Page object for the page of a specific stock.
 */
public class StockPage extends BasePage<StockPage> {

  private static final String VALUE_CELL_CSS_LOCATOR_TEMPLATE = "td[data-xm-qa-name='%s']";

  private static final By MARGIN_REQUIREMENT_CELL =
      By.cssSelector(VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("margin_requirement__value"));
  private static final By SPREAD_CELL =
      By.cssSelector(VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("spreads_as_low_as__value"));
  private static final By SYMBOL_CELL =
      By.cssSelector(VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("symbols__value"));
  private static final By TRADE_SIZE_CELL =
      By.cssSelector(VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("min_max_trade_size__value"));
  private static final By DESCRIPTION_CELL =
      By.cssSelector(VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("description__value"));
  private static final By LONG_SWAP_CELL =
      By.cssSelector(
          VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("swap_value_in_margin_currency_long__value"));
  private static final By SHORT_SWAP_CELL =
      By.cssSelector(
          VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("swap_value_in_margin_currency_short__value"));
  private static final By LIMIT_AND_STOP_LEVELS_CELL =
      By.cssSelector(
          VALUE_CELL_CSS_LOCATOR_TEMPLATE.formatted("limit_and_stop_levels__title") + " > span");

  private final Stock stock;

  private final TopBar topBar;

  protected StockPage(Stock stock,
                      WebUiProperties webUiProperties,
                      WebDriverProvider driverProvider,
                      ElementActions elementActions) {
    super("stocks/" + stock.symbol().toLowerCase(), webUiProperties, driverProvider,
        elementActions);

    this.stock = stock;

    this.topBar = new TopBar(elementActions);
  }

  @Override
  public void isLoaded() throws Error {
    super.isLoaded();

    assertThat(topBar.hasHeader(stock.description().toUpperCase()))
        .as("Check that Stock page is loaded")
        .isTrue();
  }

  public String getMinMarginPercentage() {
    return getCellValue(MARGIN_REQUIREMENT_CELL);
  }

  public String getSpread() {
    return getCellValue(SPREAD_CELL);
  }

  public String getSymbol() {
    return getCellValue(SYMBOL_CELL);
  }

  public String getTradeSize() {
    return getCellValue(TRADE_SIZE_CELL);
  }

  public String getDescription() {
    return getCellValue(DESCRIPTION_CELL);
  }

  public String getLongSwap() {
    return getCellValue(LONG_SWAP_CELL);
  }

  public String getShortSwap() {
    return getCellValue(SHORT_SWAP_CELL);
  }

  public String getLimitAndStopLevels() {
    return getCellValue(LIMIT_AND_STOP_LEVELS_CELL);
  }

  private String getCellValue(By cellLocator) {
    return elementActions.find(cellLocator).getAttribute("textContent").trim();
  }
}
