#  Scenario: Select an event to edit it
#    Given I am in the page that i select the wanted event to edit from it
#    When I select the wanted event to edit
#    And I click edit the selected event
#    Then Show all event information in the editing page to edit the wanted ones
#
#Scenario: Adding an image, video or media when editing an event
#Given I am in the editing page
#When I click add image, add video or add media
#Then Open files to choose the item
#And Display the image on it specific place
#
#Scenario: Removing an image, video or media when editing an event
#Given I am in the editing page
#When I click on the image, video or media
#And I click remove media
#Then Remove it

#  Scenario: No changes on the event when editing it
#    Given I am in the editing page
#    When No changes done on the event
#    And I click edit
#    Then Show me an information message
#    And Dont do any thing
#
#  Scenario Outline: Editing event as a service provider
#    When I am in editing page
#    And I fill in 'Title' with  "<Title>"
#    And I fill in 'Details' with "<Details>"
#    And I fill in 'EventCategory' with "<EventCategory>"
#    And I fill in 'Price' with "<Price>"
#    And I fill in 'Place' with "<Place>"
#    And I fill in 'StartTime' with "<StartTime>"
#    And I fill in 'EndTime' with "<EndTime>"
#    And I fill in 'BookingTime' with "<BookingTime>"
#    And I click edit
#    Then A "<Message>" should appear
#
#    Examples:
#      | Title        | Details | EventCategory | Price | Place        | StartTime | EndTime | BookingTime | Message                                                                                              |
#      | first event  | welcome | Birthdays     | 3000  | Paris Palace | 2:00 PM   | 6:00 PM  | 2           | Event added successfully                                                                            |
#      |              | enjoy   | Birthdays     | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  | 2           | You should enter a title for the event                                                              |
#      | new event    |         | Birthdays     | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  | 2           | You should enter details for the event                                                              |
#      | new event    | enjoy   |               | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  | 2           | You should enter an event category for the event                                                    |
#      | new event    | enjoy   | Birthdays     |       | Jawhara      | 2:00 PM   | 6:00 PM  | 2           | You should enter a price for the event                                                              |
#      | new event    | enjoy   | Birthdays     | 3000  |              | 2:00 PM   | 6:00 PM  | 2           | You should enter a place for the event                                                              |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      |           | 6:00 PM  | 2           | You should enter a start time for the event                                                         |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 2:00 PM   |          | 2           | You should enter an end time for the event                                                          |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  |             | You should enter a booking time for the event                                                       |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 4:00 PM   | 7:00 AM  | 2           | End time should be after start time                                                                 |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  | 6           | Booking time should be less than the time interval of the event                                     |
#      | new event    | enjoy   | Birthdays     | omar  | Jawhara      | 2:00 PM   | 6:00 PM  | 2           | Price should be integer value                                                                       |
#      | new event    | enjoy   | Birthdays     | 3000  | Jawhara      | 2:00 PM   | 6:00 PM  | omar        | Booking time should be integer value                                                                |
#      | Second event | on fire | Birthdays     | 2000  | Paris Palace | 10:00 AM  | 4:00 PM  | 2           | Schedule conflicts between the time interval of the event and the time interval of the other events |
