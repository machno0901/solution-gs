package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefs {

  private final WebDriver driver;
  private Long productId;
  private final BagPage bagPage;
  private final ProductDisplayPage productDisplayPage;

  public ProductStepDefs(BagPage bagPage, ProductDisplayPage productDisplayPage) {
    this.driver = Hooks.getDriver();
    this.bagPage = bagPage;
    this.productDisplayPage = productDisplayPage;
  }

  @Given("the user is on a product page")
  public void theUserIsOnAProductPage() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    productDisplayPage.waitForProductDisplayPage();
  }

  @When("adding the product to the Bag")
  public void addingTheProductToTheBag() {
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @Then("the product should appear in the Bag")
  public void theProductShouldAppearInTheBag() {
    bagPage.waitForBagPage();
    List<Long> variantIds = bagPage.getVariantIdsInBag();
    assertThat(variantIds).as("Expected product is in Bag").contains(productId);
  }

  @Then("there are products in the bag")
  public void thereAreProductsInTheBag() {
    addingTheProductToTheBag();
    theProductShouldAppearInTheBag();
  }

  @When("I remove a product")
  public void removeAProduct() {
    bagPage.removeProduct();
  }

  @Then("the product is removed from the bag")
  public void theProductIsRemovedFromTheBag() {
    bagPage.verifyBagIsEmpty();
  }

  @When("I set quantity to {}")
  public void setQuantity(int quantity) {
    bagPage.setQuantity(quantity);
  }

  @Then("product quantity is changed")
  public void productQuantityIsIncreased() {
    // works for quantities starting from 2
    bagPage.waitUntilTotalPriceIs("£" + bagPage.getProductQuantity() * 30);
  }
}
