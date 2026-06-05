Feature: Category creation

  Background:
    * def baseUrl = 'http://localhost:8080/categories'

  Scenario: Create category with valid data
    Given url baseUrl
    And request
    """
    {
      "name": "Electronics",
      "description": "All electronic products"
    }
    """
    When method post
    Then status 201
    And match response.name == "Electronics"
    And match response.description == "All electronic products"
    And match response.id != null

  Scenario: Create category with empty name
    Given url baseUrl
    And request
    """
    {
      "name": "",
      "description": "Test"
    }
    """
    When method post
    Then status 400
    And match response.error.name == "Le nom de la catégorie ne peut pas être vide"