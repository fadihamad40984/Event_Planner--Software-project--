Feature: Sign In

  Scenario: Successful User Sign In
    Given the user is in the sign-in page
    When the user enters their email address and password
    And clicks on the sign-in button
    Then the user should be redirected to their dashboard

  Scenario: Successful Admin Sign In
    Given the user is in the sign-in page
    When the admin enters their admin email address and password
    And clicks on the sign-in button
    Then the admin should be redirected to the admin dashboard

  Scenario: Successful Service Provider Sign In
    Given the user is in the sign-in page
    When the service provider enters their email address and password
    And clicks on the sign-in button
    Then the service provider should be redirected to the service provider dashboard

  Scenario: Incorrect Email Address
    Given the user is in the sign-in page
    When the user enters incorrect Email Address
    And clicks on the sign-in button
    Then the user should receive an error message indicating invalid Email Address


  Scenario: Incorrect Password
    Given the user is in the sign-in page
    When the user enters incorrect Password
    And clicks on the sign-in button
    Then the user should receive an error message indicating invalid Password


  Scenario: Forgotten Password
    Given the user is in the sign-in page
    When the user clicks on the "Forgot Password" link
    And enters their email address
    And submits the form
    Then the user should receive an email with instructions to reset their password

  Scenario: Account Lockout
    Given the user attempts to sign in with incorrect (Email Address or Password) multiple times
    Then the user account should be temporarily locked out
    And the user should receive a notification about the lockout time


