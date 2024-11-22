@order4
Feature: Database Connection

  Scenario: Successful connection
    When I want to connect to database
    And I fill in 'port' with '5432'
    And I fill in 'databaseName' with 'postgres'
    And I fill in 'username' with 'postgres'
    And I fill in 'password' with 'SfE#76132'
    Then I should see "Connected To The Database Successfully" for connection

  Scenario: Failure connection
    When I want to connect to database
    And I fill in 'port' with '3300'
    And I fill in 'databaseName' with 'invalidName'
    And I fill in 'username' with 'invalidRoot'
    And I fill in 'password' with 'invalidPassword'
    Then I should see "Couldn't Connect To The Database" for connection