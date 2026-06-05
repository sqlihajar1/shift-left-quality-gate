Feature: Get products

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Get all products
    Given url baseUrl  + '/products'
    When method get
    Then status 200
    And match response == '#[]'

  Scenario: Scenario: Get product by valid ID without category
    Given url baseUrl  + '/products'
    And request
    """
    {
      "name": "Phone",
      "description": "Smartphone",
      "price": 1000,
      "stockQuantity": 10
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/products/' + productId
    When method get
    Then status 200
    And match response.id == productId
    And match response.name != null


  Scenario: Get product with category
    Given url baseUrl + '/categories'
    And request
    """
    {
      "name": "Electronics",
      "description": "Test"
    }
    """
    When method post
    Then status 201
    * def categoryId = response.id

    Given url baseUrl  + '/products'
    And request
    """
    {
      "name": "Laptop",
      "description": "Gaming laptop",
      "price": 5000,
      "stockQuantity": 5,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/products/' + productId
    When method get
    Then status 200
    And match response.id == productId

  Scenario: Get product with non existing ID
    Given url baseUrl + '/products/' + '/9999'
    When method get
    Then status 404
    And match response.status == 404
    And match response.error == "Product not found"