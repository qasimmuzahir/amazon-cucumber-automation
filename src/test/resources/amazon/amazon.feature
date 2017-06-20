Feature: Amazon automated testing

Scenario: Search a product on Amazon

Given user opens amazon homepage "www.amazon.com"
And user search text "Nikon" in search field
And user sorts displayed products from highest to lowest price
When user clicks on the second product from the displayed products list
Then user verifies the product name contains text "Nikon D3X" 
And user closes the browser