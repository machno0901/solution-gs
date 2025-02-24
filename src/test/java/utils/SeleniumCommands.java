package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import stepdefs.hooks.Hooks;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SeleniumCommands {

  private static final SeleniumCommands instance = new SeleniumCommands();

  public void waitForAndSendKeys(By selector, CharSequence... input) {
    waitForAndGetVisibleElementLocated(selector).sendKeys(input);
  }

  public void waitForAndClickOnElementLocated(By selector) {
    waitForElementToBeClickableLocated(selector).click();
  }

  public WebElement waitForAndGetVisibleElementLocated(By selector) {
    return until(ExpectedConditions.visibilityOfElementLocated(selector));
  }

  public void waitForElementToDisappear(By selector) {
    until(ExpectedConditions.invisibilityOfElementLocated(selector));
  }

  public List<WebElement> waitForAndGetAllVisibleElementsLocated(By selector) {
    return until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
  }

  public WebElement waitForElementToBeClickableLocated(By selector) {
    return until(ExpectedConditions.elementToBeClickable(selector));
  }

  public String getAttributeFromElement(WebElement element, String attribute) {
    return element.getAttribute(attribute);
  }

  public void selectFromDropdown(By locator, String value) {
    WebElement selectElement = Hooks.getDriver().findElement(locator);
    Select select = new Select(selectElement);
    select.selectByValue(value);
  }

  public void waitForTextToBeEqual(By locator, String expectedText) {
    Wait<WebDriver> wait = new FluentWait<>(Hooks.getDriver())
            .withTimeout(Duration.ofSeconds(30))
            .pollingEvery(Duration.ofSeconds(1))
            .ignoring(Exception.class);

    wait.until(driver -> {
      WebElement element = driver.findElement(locator);
      return element.getText().equals(expectedText);
    });
  }

  private WebDriverWait defaultWait() {
    return new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(30));
  }

  private <V> V until(Function<? super WebDriver, V> expectedCondition) {
    return defaultWait().until(expectedCondition);
  }

  public static SeleniumCommands getCommands() {
    return instance;
  }
}
