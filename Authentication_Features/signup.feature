
Feature: sign-Up

  Scenario Outline: sign-up with various inputs
    When user is in sign-up page
    And he fills in 'username' with "<Username>" for register
    And he fills in 'firstName' with "<FirstName>" for register
    And he fills in 'lastName' with "<LastName>" for register
    And he fills in 'phoneNumber' with "<PhoneNumber>" for register
    And he fills in 'password' with "<Password>" for register
    And he fills in 'email' with "<Email>" for register
    And he fills in 'userType' with "<userType>" for register
    And he submits the registration form
    Then he should see "<Message>" for register

    Examples:
      | Username | FirstName | LastName | PhoneNumber | Password     | Email            | userType         | Message                          |
      | fadi28   | Fadi      | Hamad    | 0595778769  | 1234**Fa     | fadi22@gmail.com | admin            | User was registered successfully |
      | fadi18   | Fadi      | Hamad    | 059501402   | 1234**Aa     | fadi18@gmail.com | admin            | Invalid phone number             |
      | fadi12   | Fadi      | Hamad    | 059501402a  | 1234**Aa     | fadi12@gmail.com | admin            | Invalid phone number             |
      | fadi28   | Fadi      | Hamad    | 0595014020  | weakPassword | fadi28@gmail.com | admin            | Invalid password                 |
      | fadi20   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi22ail.com    | admin            | Invalid email address            |
      | fadi28   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi28@gmail.com | admin            | Username is already taken        |
      |          | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi29@gmail.com | admin            | Username can't be empty          |
      | fadi19   |           | Hamad    | 0595014020  | 1234**Aa     | fadi29@gmail.com | admin            | First name can't be empty        |
      | fadi19   | Fadi      |          | 0595014020  | 1234**Aa     | fadi29@gmail.com | admin            | Last name can't be empty         |
      | fadi19   | Fadi      | Hamad    |             | 1234**Aa     | fadi29@gmail.com | admin            | Phone number can't be empty      |
      | fadi19   | Fadi      | Hamad    | 0595014020  |              | fadi29@gmail.com | admin            | Password can't be empty          |
      | fadi19   | Fadi      | Hamad    | 0595014020  | 1234**Aa     |                  | admin            | Email address can't be empty     |
      | fadi28   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi22@gmail.com |                  | User type can't be empty         |
      | fadi21   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi22@gmail.com | customer         | User was registered successfully |
      | fadi22   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi22@gmail.com | service provider | User was registered successfully |
      | fadi28   | Fadi      | Hamad    | 0595014020  | 1234**Aa     | fadi22@gmail.com | none             | Invalid user type                |









