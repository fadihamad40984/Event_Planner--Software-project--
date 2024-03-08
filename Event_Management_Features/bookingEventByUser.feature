Feature: Booking Event By User

  Scenario Outline: Booking event as a user
    When user is in booking page
    And he fills in 'serviceTitle' with "<ServiceTitle>"
    And he fills in 'date' with "<Date>"
    And he fills in 'time' with "<Time>"
    And he fills in 'description' with "<Description>"
    And he fills in 'attendeeCount' with "<AttendeeCount>"
    And he fills in 'guestList' with "<GuestList>"
    And he fills in 'image' with "<image>"
    And he click book
    Then he should see "<Message>"


    Examples:
      | ServiceTitle   | Date         | Time    | Description               | AttendeeCount | GuestList                                                                                     | image                                                                  | Message                                                                                                             |
      | first event    | 19-03-2024   | 14:00   | Birthday party invitation | 15            | Ahmad,Ali,Adnan,Amer,Basil,Diyaa,Ehab,Faisal,Hani,Imad,Jaber,Khalil,Loay,Mohammad,Mahmoud     | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Event booked successfully                                                                                           |
      |                | 25-03-2024   | 16:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should choose a service                                                                                         |
      | first event    |              | 16:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should enter a date for the event                                                                               |
      | first event    | 25-03-2024   |         | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should enter a time for the event                                                                               |
      | first event    | 25-03-2024   | 16:00   |                           | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should enter a description for the event                                                                        |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation |               | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should enter an attendee count for the event                                                                    |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | 10            |                                                                                               | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | You should enter a guest list for the event                                                                         |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | omar          | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Attendee count should be integer value                                                                              |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\omar.jpg,C:\Users\User\Downloads\party.jpg     | Image C:\Users\User\Downloads\omar.jpg not found                                                                    |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami                                           | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Guest list members count should be equal to attendee count                                                          |
      | first event    | 25-03-2024   | 19:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Time of the event should be in the interval of the chosen service                                                   |
      | first event    | 19-03-2024   | 15:00   | Birthday party invitation | 10            | Ahmad,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                      | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Schedule conflicts between the booking time of the event and the booking time of the other event from same service  |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | 50            | A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,BB,CC,DD,EE,FF,GG,HH,II,JJ,KK,LL,MM,NN | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Attendee count should be less than or equal the capacity of the place of the service                                |
      | first event    | 25-03-2024   | 16:00   | Birthday party invitation | 10            | Ahmad5,Asad,Basem,Bassam,Ehsan,Fadi,Hasan,Mousa,Rami,Ziad                                     | C:\Users\User\Downloads\Birthday.jpg,C:\Users\User\Downloads\party.jpg | Guest names must contain only alphabet letters                                                                      |

