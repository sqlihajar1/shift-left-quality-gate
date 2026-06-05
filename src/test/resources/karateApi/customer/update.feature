Feature: Update customer

  Background:
    * def baseUrl = 'http://localhost:8080/customers'

  Scenario: Update customer with valid ID
    Given url baseUrl
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "update.test@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/' + customerId
    And request
    """
    {
      "firstName": "Updated Hajar",
      "lastName": "Updated Malki",
      "email": "update.test@gmail.com",
      "phone": "0700000000"
    }
    """
    When method put
    Then status 200
    And match response.id == customerId
    And match response.firstName == "Updated Hajar"

  Scenario: Update customer with non existing ID
    Given url baseUrl + '/' + '9999'
    And request
    """
    {
      "firstName": "Test",
      "lastName": "User",
      "email": "test@gmail.com",
      "phone": "0600000000"
    }
    """
    When method put
    Then status 404
    And match response.status == 404
    And match response.error == "Customer not found"
