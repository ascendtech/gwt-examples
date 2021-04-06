Feature: Remove task from the list
  Scenario Outline: As a user, I want to remove a task from my ToDo list.
    Given I am on the ToDo list page
      And the task "<task>" is on my ToDo list
    When I put a check box next to my task "<task>" to remove
      And I click the Remove button
    Then  the task "<task>" will be removed from my ToDo list
    Examples:
      | task |
      | Get stuff from store |