@order10
Feature: Booking Event By User

  Scenario Outline: Booking event as a user
    When user is in booking page
    And he fills in 'serviceTitle' with "<ServiceTitle>"
    And he fills in 'date' with "<Date>"
    And he fills in 'time' with "<Time>"
    And he fills in 'description' with "<Description>"
    And he fills in 'vendors' with "<Vendors>"
    And he fills in 'balance' with "<Balance>"
    And he fills in 'attendeeCount' with "<AttendeeCount>"
    And he fills in 'guestList' with "<GuestList>"
    And he fills in 'image' with "<image>"
    And he fills in 'user' with "<User>"
    And he click book
    Then he should see "<Message>"


    Examples:
      | ServiceTitle | Date       | Time  | Description               | Vendors              | Balance | AttendeeCount | GuestList                                                                                                         | image                                                                  | User   | Message                                                                                                            |
      | first event  | 19-03-2024 | 14:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 15            | Ahmad,Ali,Adnan,Amer,Basil,Diyaa,Ehab,Faisal,Hani,Imad,Jaber,Khalil,Loay,Mohammad,Mahmoud                         | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Event booked successfully                                                                                          |
      |              | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should choose a service                                                                                        |
      | first event  |            | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter a date for the event                                                                              |
      | first event  | 25-03-2024 |       | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter a time for the event                                                                              |
      | first event  | 25-03-2024 | 16:00 |                           | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter a description for the event                                                                       |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   |               | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter an attendee count for the event                                                                   |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 |         | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter a balance for the event                                                                           |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 0             |                                                                                                                   | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | You should enter a guest list for the event                                                                        |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | omar          | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Attendee count should be integer value                                                                             |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | omar    | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | balance should be integer value                                                                                    |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 10000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | balance should be enough for vendors an event service                                                              |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\omar.jpg,C:\Users\User\Downloads\party.jpg     | fadi21 | Image C:\Users\User\Downloads\omar.jpg not found                                                                   |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami                                                               | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Guest list members count should be equal to attendee count                                                         |
      | first event  | 25-03-2024 | 19:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Time of the event should be in the interval of the chosen service                                                  |
      | first event  | 19-03-2024 | 15:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Schedule conflicts between the booking time of the event and the booking time of the other event from same service |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 50            | A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,BB,CC,DD,EE,FF,GG,HH,II,JJ,KK,LL,MM,NN,a,a,a,a,a,a,a,a,a,a | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Attendee count should be less than or equal the capacity of the place of the service                               |
      | first event  | 25-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad5,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                         | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Guest names must contain only alphabet letters                                                                     |
      | second event | 19-03-2024 | 14:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                                          | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | vendor mohammad12 is not available at this time                                                                    |
      | first event  | 19-03-2024 | 16:00 | Birthday party invitation | mohammad12,ahmad1999 | 20000   | 15            | Ahmad,Ali,Adnan,Amer,Basil,Diyaa,Ehab,Faisal,Hani,Imad,Jaber,Khalil,Loay,Mohammad,Mahmoud                         | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | fadi21 | Event booked successfully                                                                                          |
      


# we need to add vendor and balance
  # vendor service price and event service should be compatible
  #vendors are optional