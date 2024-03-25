Feature: Delete Event Service

  Scenario Outline: Event deleting
    Given I am in deletion page
    When I enter in 'Event_ID' the value "<Event_ID>"
    And I click delete
    Then A "<Message>" must appear

    Examples:
      | Event_ID | Message                            |
      | 17       | Event service deleted successfully |

