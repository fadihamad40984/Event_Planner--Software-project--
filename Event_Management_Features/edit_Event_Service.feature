Feature: Event Creation And Management

  Scenario Outline: Editing event as a service provider
    When I am in edition page
    And I am fills in 'id' with "<Id>"
    And I am fills in 'title' with "<Title>"
    And I am fills in 'details' with "<Details>"
    And I am fills in 'eventCategory' with "<EventCategory>"
    And I am fills in 'price' with "<Price>"
    And I am fills in 'place' with "<Place>"
    And I am fills in 'startTime' with "<StartTime>"
    And I am fills in 'endTime' with "<EndTime>"
    And I am fills in 'bookingTime' with "<BookingTime>"
    And I am click edit
    Then A "<Message>" should appear

    Examples:
      | Id | Title       | Details          | EventCategory | Price | Place        | StartTime | EndTime | BookingTime | Message                    |
      | 3  | first event | welcome everyone | Birthdays     | 3000  | Paris Palace | 14:00     | 18:00   | 2           | Event updated successfully |

