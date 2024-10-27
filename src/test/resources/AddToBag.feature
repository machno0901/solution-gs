Feature: Add To Bag
  Background:
    Given the user is on a product page

  @Example
  Scenario: Adding a product to the Bag
    When adding the product to the Bag
    Then the product should appear in the Bag

    @AC1
    Scenario: Removing a product from the Bag
      Given there are products in the bag
      When I remove a product
      Then the product is removed from the bag

    @AC2
    Scenario: Increasing product quantity
      Given there are products in the bag
      When I set quantity to 5
      Then product quantity is changed

    @AC3
    Scenario: Decreasing product quantity
      Given there are products in the bag
      When I set quantity to 5
      Then product quantity is changed
      When I set quantity to 3
      Then product quantity is changed
