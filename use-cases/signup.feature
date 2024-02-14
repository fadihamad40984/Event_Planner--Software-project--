Feature: Signup

  Scenario: Successful Signup
    Given I am on the signup page
    When I fill the signup form with valid details
    And I submit the form
    Then I should receive a confirmation email
    And I should be redirected to the login page

  Scenario: Existing Email Address
    Given I am on the signup page
    When I fill the signup form with an email address that already exists
    And I submit the form
    Then I should see an error message indicating the email address is already in use

  Scenario: Weak Password
    Given I am on the signup page
    When I fill the signup form with a weak password
    And I submit the form
    Then I should see an error message indicating the password requirements

  Scenario: Incomplete Form
    Given I am on the signup page
    When I fill the signup form but leave some required fields blank
    And I submit the form
    Then I should see error messages indicating the missing fields


  Scenario: Confirmation Email Delay
    Given I am on the signup page
    And I have filled in the signup form with valid details
    When I submit the form
    But the confirmation email is delayed
    Then I should have the option to request a resend of the confirmation email




