Feature:   Navigating with the keyboard.
  Scenario Outline: As a user, I want to be able to navigate to different focusable items on the keyboard.
    Given I am on the iCite page.
    When I click the HOME button.
    And I click in the SEARCH field.
    And I click the TAB button.
    Then I will be moved to the next focusable field.


