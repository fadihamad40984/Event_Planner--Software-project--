Feature: Review Vendor

  Scenario Outline: Customer Reviewing Vendor
    When customer is in reviewing page
    And customer fills in 'vendorUserName' with "<VendorUserName>"
    And customer fills in 'customerUserName' with "<CustomerUserName>"
    And customer fills in 'rating' with "<Rating>"
    And customer fills in 'feedBackText' with "<FeedBackText>"
    And customer click confirm review
    Then customer should see "<Message>"


    Examples:
      | VendorUserName | CustomerUserName | Rating | FeedBackText    | Message                              |
      | mohammad12     | fadi21           | *****  | beautiful voice | vendor review confirmed successfully |
      | ahmad1999      | fadi21           | *      | beautiful voice | vendor review confirmed successfully |


    # rating will be given as choices in the menu
  # FeedBackText is optional
  # VendorUserName & CustomerUserName are given from previous steps

