Feature: Reset functionality on login page of Application

  Scenario Outline: Verification of reset button with numbers of credential
    Given Open the Firefox and launch the application
    When Enter the user "<username>" and password "<password>"
    Then Reset the credential
    Examples:
      | username | password  |
      | User1    | password1 |
      | User2    | password2 |
      | User3    | password3 |
