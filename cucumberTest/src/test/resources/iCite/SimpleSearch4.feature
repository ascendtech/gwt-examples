Feature: Searching by date range.
  Scenario Outline: As a user, I want to perform a search by a date range.
    Given I am on the iCite page.
    When I click advancedFilter.
    And I click the From box.
    And I enter 2021-03-03.
    And I click the To box.
    And I enter 2021-04-26.
    And I click the Apply filters button.
    Then all publications will be displayed.


