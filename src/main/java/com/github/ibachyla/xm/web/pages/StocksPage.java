package com.github.ibachyla.xm.web.pages;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import com.github.ibachyla.xm.web.elements.TopBar;
import com.github.ibachyla.xm.web.models.Stock;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Lazy
@Component
public class StocksPage extends BasePage<StocksPage> {

    private static final String URL = "stocks";
    private static final String HEADER = "STOCKS";

    private static final String COUNTRY_FILTER_CSS_LOCATOR_TEMPLATE = ".table-country-filter > button[data-value='%s']";
    private static final String STOCK_CELL_CSS_LOCATOR_TEMPLATE = "td[data-xm-qa-name='%s']";

    private static final By SYMBOL_CELL = By.cssSelector(STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("symbol"));
    private static final By DESCRIPTION_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("symbolWithDescription"));
    private static final By SPREAD_CELL = By.cssSelector(STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("minSpread"));
    private static final By TRADE_SIZE_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("minMaxTradeSize"));
    private static final By MIN_MARGIN_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("marginRequirement"));
    private static final By LONG_SWAP_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("swapLong"));
    private static final By SHORT_SWAP_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("swapShort"));
    private static final By LIMIT_STOP_LEVELS_CELL = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("limitStopLevel"));
    private static final By READ_MORE_LINK = By.cssSelector(
            STOCK_CELL_CSS_LOCATOR_TEMPLATE.formatted("url") + " > a");

    private static final By NEXT_PAGE_BUTTON = By.cssSelector(".paginate_button.next");

    private final TopBar topBar;

    protected StocksPage(WebUiProperties webUiProperties,
                         WebDriverProvider driverProvider,
                         ElementActions elementActions) {
        super(URL, webUiProperties, driverProvider, elementActions);

        this.topBar = new TopBar(elementActions);
    }

    @Override
    public void isLoaded() throws Error {
        super.isLoaded();

        assertThat(topBar.hasHeader(HEADER))
                .as("Check that Stocks page is loaded")
                .isTrue();
    }

    public void filterStocksByCountry(String country) {
        log.info("Filtering stocks by country: {}", country);
        elementActions.click(countryFilter(country));
    }

    public boolean isCountryFilterSelected(String country) {
        return elementActions.containsClass(countryFilter(country), "active");
    }

    public Optional<Stock> findStock(String symbol) {
        log.info("Finding stock with symbol: {}", symbol);

        Optional<WebElement> symbolCell;

        do {
            symbolCell = findSymbol(symbol);
            if (isLastPage()) break;
            elementActions.click(NEXT_PAGE_BUTTON);
        } while (symbolCell.isEmpty());

        return symbolCell.map(this::createStock);
    }

    public void readMore(String symbol) {
        log.info("Selecting 'Read More' for stock with symbol: {}", symbol);

        WebElement stockRow = findSymbol(symbol)
                .map(this::getParentRow)
                .orElseThrow(() -> new NoSuchElementException("Stock not found"));

        WebElement readMoreLink = stockRow.findElement(READ_MORE_LINK);
        if(elementActions.isVisible(readMoreLink)) {
            elementActions.click(readMoreLink);
            return;
        }

        elementActions.click(stockRow.findElement(DESCRIPTION_CELL));
        readMoreLink = stockRow.findElement(By.xpath("./following-sibling::tr//a"));
        elementActions.click(readMoreLink);
    }

    private boolean isLastPage() {
        return elementActions.containsClass(NEXT_PAGE_BUTTON, "disabled");
    }

    private Optional<WebElement> findSymbol(String symbol) {
        return elementActions.findAll(SYMBOL_CELL)
                .stream()
                .filter(cell -> cell.getText().equals(symbol))
                .findFirst();
    }

    private Stock createStock(WebElement symbolCell) {
        WebElement stockRow = getParentRow(symbolCell);

        return new Stock(
                symbolCell.getText(),
                getStockValue(stockRow, DESCRIPTION_CELL),
                getStockValue(stockRow, SPREAD_CELL),
                getStockValue(stockRow, TRADE_SIZE_CELL),
                getStockValue(stockRow, MIN_MARGIN_CELL),
                getStockValue(stockRow, LONG_SWAP_CELL),
                getStockValue(stockRow, SHORT_SWAP_CELL),
                getStockValue(stockRow, LIMIT_STOP_LEVELS_CELL)
        );
    }

    private WebElement getParentRow(WebElement cell) {
        return cell.findElement(By.xpath("./parent::tr"));
    }

    private String getStockValue(WebElement stockRow, By cellLocator) {
        return stockRow.findElement(cellLocator).getAttribute("textContent").trim();
    }

    private By countryFilter(String country) {
        return By.cssSelector(String.format(COUNTRY_FILTER_CSS_LOCATOR_TEMPLATE, country));
    }
}
