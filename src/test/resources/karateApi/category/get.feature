Feature: Get categories

  Background:
    * def baseUrl = 'http://localhost:8080/categories'

  Scenario: Get all categories
    Given url baseUrl
    When method get
    Then status 200
    And match response == '#[]'


  Scenario: Get category by valid ID
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
    When method get
    Then status 200
    And match response.id == categoryId
    And match response.name != null

  Scenario: Get category with non existing ID
    Given url baseUrl + '/9999'
    When method get
    Then status 404
    And match response.status == 404
    And match response.error == "Category not found"

