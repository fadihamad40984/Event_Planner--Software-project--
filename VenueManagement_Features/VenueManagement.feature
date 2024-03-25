Feature: Venue Management

  Scenario Outline: Organizer adds a new venue
    Given I am in the adding new venues page
    When the organizer clicks on the button to add a new venue
    And the organizer fills in 'venue_name' details  "<venue_name>"
    And the organizer fills in 'capacity' details  "<capacity>"
    And the organizer fills in 'amenities' details  "<amenities>"
    Then i click add
    And the "<Message>" should appear

    Examples:
      | venue_name        | capacity | amenities                    | Message                             |
      | Paris Palace      | 40       | WiFi,DJ, Catering,Cake       | Venue added successfully            |
      | nablus            | 40       | WiFi,DJ, Catering,Cake       | Venue added successfully            |
      |                   | 150      | WiFi,Cake,Catering           | venue name can't be empty           |
      | gold hotel        |          | WiFi,DJ, Catering            | capacity can't be empty             |
      | community center  | 200      |                              | amenities can't be empty            |
      | convention center | 300u     | WiFi,Projector, DJ, Catering | capacity should be an integer value |
