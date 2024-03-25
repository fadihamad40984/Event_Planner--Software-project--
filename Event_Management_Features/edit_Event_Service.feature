Feature: Event Creation And Management

  Scenario Outline: Editing event as a service provider
    When I am in edition page
    And I am fill in 'id' with "<Id>"
    And I am fill in 'title' with "<Title>"
    And I am fill in 'details' with "<Details>"
    And I am fill in 'eventCategory' with "<EventCategory>"
    And I am fill in 'price' with "<Price>"
    And I am fill in 'place' with "<Place>"
    And I am fill in 'startTime' with "<StartTime>"
    And I am fill in 'endTime' with "<EndTime>"
    And I am fill in 'bookingTime' with "<BookingTime>"
    And I am click edit
    Then A "<Message>" should appear

    Examples:
      | Id | Title       | Details          | EventCategory | Price | Place        | StartTime | EndTime | BookingTime | Message                    |
      | 3  | first event | welcome everyone | Birthdays     | 3000  | Paris Palace | 14:00     | 18:00   | 2           | Event updated successfully |

