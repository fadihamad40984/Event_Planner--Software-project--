Feature: Event Creation And Management

  Scenario: Choose operation
    Given I am in the event creation and management page
    When I click add choice
    Then Move to addition page
    When I click delete choice
    Then Move to deletion page
    When I click edit choice
    Then Move to editing page

  Scenario: Successful event adding
    Given I am in addition page
    When I fill the event information
    And I click add
    Then A successful message should appear
    And The new event should be added to event lists

  Scenario: Successful event deleting
    Given I am in deletion page
    When I select the wanted event to delete
    And I click delete
    Then A successful message should appear
    And The event should be deleted from event lists

  Scenario: Select an event to edit it
    Given I am in editing page
    And I click edit the selected event
    Then Show all event information in a distinct page to edit the wanted ones

  Scenario: Successful event editing
    Given I am in the distinct page that pop on the editing page
    When I change the wanted event information
    And I click edit
    Then A successful message should appear
    And The event in the event list should be updated

  Scenario: Incomplete event information
    Given I am in addition page
    When I fill the event information but leave some or all required fields blank
    And I click add
    Then I should see error message
    And The program returns me to event addition page to complete the information

  Scenario: No changes on the event when editing it
    Given I am in the distinct page that pop on the editing page
    When No changes done on the event
    And I click edit
    Then Show me an information message
    And Dont do any thing

  Scenario: Wrong date format at addition
    Given I am in addition page
    When I fill the event information
    And I click add
    Then An error message should appear
    And The program returns me to event addition page to repeat the date

  Scenario: Wrong time format at addition
    Given I am in addition page
    When I fill the event information
    And I click add
    Then An error message should appear
    And The program returns me to event addition page to repeat the time

  Scenario: Wrong value format in attendee count field at addition
    Given I am in addition page
    When I fill the event information
    And I click add
    Then An error message should appear
    And The program returns me to event addition page to repeat the attendee count

  Scenario: Wrong date format at editing
    Given I am in the distinct page that pop on the editing page
    When I change the event information
    And I click edit
    Then An error message should appear
    And The program returns me to event editing page to repeat the date

  Scenario: Wrong time format at editing
    Given I am in the distinct page that pop on the editing page
    When I change the event information
    And I click edit
    Then An error message should appear
    And The program returns me to event editing page to repeat the time

  Scenario: Wrong value format in attendee count field at editing
    Given I am in the distinct page that pop on the editing page
    When I change the event information
    And I click edit
    Then An error message should appear
    And The program returns me to event editing page to repeat the attendee count

