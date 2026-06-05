Feature: Get customers

  Background:
    * def baseUrl = 'http://localhost:8080/customers'

  Scenario: Get all customers
    Given url baseUrl
    When method get
    Then status 200
    And match response == '#[]'

  Scenario: Get customer by valid ID
    Given url baseUrl
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "hajar.test@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/' + customerId
    When method get
    Then status 200
    And match response.id == customerId
    And match response.firstName == "Hajar"

  Scenario: Get customer with non existing ID
    Given url baseUrl + '/' + '9999'
    When method get
    Then status 404
    And match response.status == 404
    And match response.error == "Customer not found"