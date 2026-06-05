Feature: Delete category

  Background:
    * def baseUrl = 'http://localhost:8080/categories'

  Scenario: Delete category with valid ID
    Given url baseUrl
    And request
    """
    {
      "name": "électronique",
      "description": "Appareils électroniques"
    }
    """
    When method post
    Then status 201
    * def categoryId = response.id
    Given url baseUrl + '/' + categoryId
    When method delete
    Then status 204

  Scenario: Delete category with non existing ID
    Given url baseUrl + '/9999'
    When method delete
    Then status 404
    And match response.status == 404
    And match response.error == "Category not found"