Feature: Make a coffee
    A customer can buy a coffee and purchase with prices. The amount of ingredients in inventory should be correctly calculated.

    Background: purchased a coffee.
        Given The coffee maker is ready to serve
    Scenario: buy a coffee with exact price.
        When The customer made an order
        Then The customer choose recipe number 2 and purchase with 100 so the change will be 0
    Scenario: buy a coffee that is not on the menu.
        When The customer made an unexisting order
        Then The customer choose recipe number 0 and purchase with 100 so the change will be 100
    Scenario: buy a coffee that price is over your cost.
        When The customer made an order
        Then The customer choose recipe number 1 and purchase with 1 so the change will be 1
