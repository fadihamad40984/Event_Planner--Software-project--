Feature: Event Creation And Management

  Scenario Outline: Adding event as a service provider
    When I am in addition page
    And I am fills in 'title' with "<Title>"
    And I am fills in 'details' with "<Details>"
    And I am fills in 'eventCategory' with "<EventCategory>"
    And I am fills in 'price' with "<Price>"
    And I am fills in 'place' with "<Place>"
    And I am fills in 'startTime' with "<StartTime>"
    And I am fills in 'endTime' with "<EndTime>"
    And I am fills in 'bookingTime' with "<BookingTime>"
    And I am click add
    Then A "<Message>" should appear



    Examples:
      | Title        | Details | EventCategory | Price | Place        | StartTime | EndTime | BookingTime | Message                                                                                                             |
      | first event  | welcome | Birthdays     | 3000  | Paris Palace | 14:00     | 18:00   | 2           | Event added successfully                                                                                            |
      |              | enjoy   | Birthdays     | 3000  | Jawhara      | 14:00     | 18:00   | 2           | You should enter a title for the event                                                                              |
      | new event    |         | Birthdays     | 3000  | Jawhara      | 14:00     | 18:00   | 2           | You should enter details for the event                                                                              |
      | new event    | enjoy   |               | 3000  | Jawhara      | 14:00     | 18:00   | 2           | You should enter an event category for the event                                                                    |
      | new event    | enjoy   | Birthdays     |       | Jawhara      | 14:00     | 18:00   | 2           | You should enter a price for the event                                                                              |
      | new event    | enjoy   | Birthdays     | 3000  |              | 14:00     | 18:00   | 2           | You should enter a place for the event                                                                              |
      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      |           | 18:00   | 2           | You should enter a start time for the event                                                                         |
      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 14:00     |         | 2           | You should enter an end time for the event                                                                          |
      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 14:00     | 18:00   |             | You should enter a booking time for the event                                                                       |
      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 14:00     | 7:00    | 2           | End time should be after at least BookingTime hours from start time                                                 |
      | new event    | enjoy   | Birthdays     | omar  | Jawhara      | 14:00     | 18:00   | 2           | Price should be integer value                                                                                       |
      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 14:00     | 18:00   | omar        | Booking time should be integer value                                                                                |
      | Second event | on fire | Birthdays     | 2000  | Paris Palace | 10:00     | 16:00   | 2           | Schedule conflicts between the time interval of the event service and the time interval of the other event services |


