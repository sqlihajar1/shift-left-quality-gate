Feature: Delete customer

  Background:
    * def baseUrl = 'http://localhost:8080/customers'

  Scenario: Delete customer with valid ID
    Given url baseUrl
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "delete.test@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/' + customerId
    When method delete
    Then status 204

  Scenario: Delete customer with non existing ID
    Given url baseUrl + '/' + '9999'
    When method delete
    Then status 404
    And match response.status == 404
    And match response.error == "Customer not found"