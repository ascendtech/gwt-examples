Feature:   Performing a search.
  Scenario: As a user, I want to perform a Search.
    Given I am on the iCite page.
    When I click my mouse in the Search query field.
    And I enter pcsk9.
    And I click the magnifying glass.
    Then the results for pcsk9 will be displayed.