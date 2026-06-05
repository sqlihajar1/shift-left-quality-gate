Feature: Create customer

  Background:
    * def baseUrl = 'http://localhost:8080/customers'

  Scenario: Create customer with valid data
    Given url baseUrl
    And request
    """
    {
      "firstName": "Meryem",
      "lastName": "Malki",
      "email": "meryem.malki@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201

  Scenario: Create customer with invalid email
    Given url baseUrl
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "invalid-email",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 400
    And match response.error.email == "L'email doit être valide"

  Scenario: Create customer with empty first name
    Given url baseUrl
    And request
    """
    {
      "firstName": "",
      "lastName": "Malki",
      "email": "test1@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 400
    And match response.error.firstName == "Le prénom ne peut pas être vide"

  Scenario: Create customer with duplicate email
    Given url baseUrl
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "duplicate@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201

    Given url baseUrl
    And request
    """
    {
      "firstName": "Test",
      "lastName": "User",
      "email": "duplicate@gmail.com",
      "phone": "0611111111"
    }
    """
    When method post
    Then status 400
    And match response.error == "Email already exists"
