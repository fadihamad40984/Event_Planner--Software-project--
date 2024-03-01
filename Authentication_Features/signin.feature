Feature: Login
  Scenario Outline: login scenarios
    Given user is connected to the database
    When he fills in 'username' with '<username>' for login
    And he fills in 'password' with '<password>' for login
    And user clicks on login
    Then user should see '<message>' for login
    And close the connection

    Examples:
      | username  | password | message                      |
      | fadi28    | 1234**Fa | Valid username and password  |
      | fadi28    | 123456   | Invalid username or password |
      | fadihamad | 1234**Fa | Invalid username or password |
      |           | 12de456  | Invalid username or password |
      | fadihamad |          | Invalid username or password |