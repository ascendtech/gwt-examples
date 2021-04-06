Feature: Add task to the list
  Scenario Outline: As a user, I want to add a task to my ToDo list.
    Given I am on the ToDo list page
    And the task "<task>" is not on my ToDo list
    When I enter my task "<task>"
    And I click the Add button
    Then  the task "<task>" will be added to my ToDo list
    Examples:
      | task |
      | Get stuff from store |