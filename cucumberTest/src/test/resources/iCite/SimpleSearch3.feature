Feature: Open the User Guide.
  Scenario Outline: As a user, I want to perform a search using the OR function.
    Given I am on the iCite page.
    When I click my mouse in the Search query field.
    And I search for COVID or SYMPTOMS.
    And I click the magnifying glass.
    Then all results for COVID or SYMPTOMS will be displayed.


