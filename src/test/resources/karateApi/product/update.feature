Feature: Update product

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Update product without category
    Given url baseUrl + '/products'
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
    And request
    """
    {
      "name": "Updated Phone",
      "description": "Updated smartphone",
      "price": 1500,
      "stockQuantity": 20
    }
    """
    When method put
    Then status 200
    And match response.id == productId
    And match response.name == "Updated Phone"


  Scenario: Update product with category
    Given url baseUrl + '/categories'
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

    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Laptop",
      "description": "Gaming laptop",
      "price": 5000,
      "stockQuantity": 5
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/products/' + productId
    And request
    """
    {
      "name": "Updated Laptop",
      "description": "Updated gaming laptop",
      "price": 5500,
      "stockQuantity": 7,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method put
    Then status 200
    And match response.id == productId
    And match response.name == "Updated Laptop"


  Scenario: Update product with non existing ID
    Given url baseUrl + '/products/' + '9999'
    And request
    """
    {
      "name": "Updated Product",
      "description": "Test",
      "price": 1000,
      "stockQuantity": 10
    }
    """
    When method put
    Then status 404
    And match response.status == 404
    And match response.error == "Product not found"