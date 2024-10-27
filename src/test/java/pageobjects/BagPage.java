package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  private static final By REMOVE_BUTTON = By.cssSelector("[data-locator-id^='miniBag-remove']");
  private static final By PARAGRAPH_NO_PRODUCTS = By.cssSelector("[class^='empty-view_subtitle']");
  private static final By HEADER_BAG_IS_EMPTY = By.cssSelector("[class^='empty-view_title']");
  private static final By QUANTITY_DROPDOWN = By.cssSelector("[data-locator-id^='miniBag-quantityDropdown']");
  private static final By TOTAL_PRICE = By.cssSelector("[data-locator-id^='miniBag-totalValue']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  private int productQuantity;

  public void waitForBagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public void removeProduct() {
      getCommands().waitForAndClickOnElementLocated(REMOVE_BUTTON);
  }

  public void verifyBagIsEmpty() {
    getCommands().waitForElementToDisappear(BAG_ITEMS);
    getCommands().waitForAndGetVisibleElementLocated(PARAGRAPH_NO_PRODUCTS);
    getCommands().waitForAndGetVisibleElementLocated(HEADER_BAG_IS_EMPTY);
  }

  public void setQuantity(int expectedQuantity) {
    productQuantity = expectedQuantity;
    getCommands().selectFromDropdown(QUANTITY_DROPDOWN, String.valueOf(expectedQuantity));
  }

  public void waitUntilTotalPriceIs(String expectedPrice) {
    getCommands().waitForTextToBeEqual(TOTAL_PRICE, expectedPrice);
  }

  public int getProductQuantity() {
    return productQuantity;
  }
}
