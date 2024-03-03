Feature: Delete Event Service

  Scenario Outline: Event deleting
    Given I am in deletion page
    When I enter in 'Event_ID' the value "<Event_ID>"
    And I click delete
    Then A "<Message>" should appear

    Examples:
      | Event_ID | Message                            |
      | 10       | Event service deleted successfully |
      | 8        | Couldn't delete the event service  |

