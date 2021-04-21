Feature: Performing a search with AND function
Scenario Outline: As a user, I want to perform a search using the AND function.
        Given I am on the iCite page.
        When I click my mouse in the Search query field.
	    And I search for COVID and SYMPTOMS.
	    And I click the magnifying glass.
        Then all results for COVID as well as SYMPTOMS will be displayed.


