Feature: Update category

  Background:
    * def baseUrl = 'http://localhost:8080/categories'

  Scenario: Update category with valid ID
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
    * def categoryId = response.id

    Given url baseUrl + '/' + categoryId
    And request
    """
    {
      "name": "Updated Electronics",
      "description": "Updated description"
    }
    """
    When method put
    Then status 200
    And match response.id == categoryId
    And match response.name == "Updated Electronics"


  Scenario: Update category with non existing ID
    Given url baseUrl + '/9999'
    And request
    """
    {
      "name": "Updated Electronics",
      "description": "Updated description"
    }
    """
    When method put
    Then status 404
    And match response.status == 404
    And match response.error == "Category not found"