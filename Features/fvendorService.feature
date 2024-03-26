@order6
Feature: Vendor Service

  Scenario Outline: Define vendor service
    When the registering user is vendor
    And vendor fills in 'vendorUserName' with "<VendorUserName>"
    And vendor fills in 'serviceType' with "<ServiceType>"
    And vendor fills in 'serviceDescription' with "<ServiceDescription>"
    And vendor fills in 'servicePricePerHour' with "<ServicePricePerHour>"
    And vendor fills in 'serviceAvailability' with "<ServiceAvailability>"
    And vendor click confirm service
    Then vendor should see "<Message>"


    Examples:
      | VendorUserName | ServiceType | ServiceDescription             | ServicePricePerHour | ServiceAvailability | Message                                  |
      | mohammad12     | dj          | excellent instruments          | 2500                | yes                 | service confirmed to vendor successfully |
      | ahmad1999      | singer      |                                | 5000                | yes                 | ServiceDescription cannot be empty       |
      | ahmad1999      | singer      | participates in over 100 party |                     | yes                 | ServicePrice cannot be empty             |
      | ahmad1999      |             | participates in over 100 party | 5000                | yes                 | ServiceType cannot be empty              |
      | ahmad1999      | singer      | participates in over 100 party | hello               | yes                 | ServicePrice must be numeric             |
      | ahmad1999      | dj          | excellent instruments          | 2000                | yes                 | service confirmed to vendor successfully |


    #ServiceType & ServiceAvailability will be given as choices in the menu
    # VendorUserName are given from previous steps



