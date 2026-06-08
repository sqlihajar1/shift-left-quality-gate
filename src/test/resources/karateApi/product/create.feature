Feature: Create product

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Create product without category
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
    And match response.name == "Phone"
    And match response.price == 1000
    And match response.stockQuantity == 10
    And match response.id != null


  Scenario: Create product with valid category
    Given url baseUrl + '/categories'
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

    Given url baseUrl + '/products'
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

  Scenario: Create product with non existing category
    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Laptop",
      "description": "Test",
      "price": 3000,
      "stockQuantity": 5,
      "category": {
        "id": 9999
      }
    }
    """
    When method post
    Then status 404
    And match response.status == 404
    And match response.error == "Category not found"

  Scenario: Create product with empty name
    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "",
      "description": "Test",
      "price": 100,
      "stockQuantity": 5
    }
    """
    When method post
    Then status 400
    And match response.error.name == "Le nom du produit ne peut pas être vide"

  Scenario: Create product with invalid price
    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Test Product",
      "description": "Test",
      "price": 0,
      "stockQuantity": 5
    }
    """
    When method post
    Then status 400
    And match response.error.price == "Le prix doit être supérieur à 0"

  Scenario: Create product with negative stock
    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Test Product",
      "description": "Test",
      "price": 100,
      "stockQuantity": -1
    }
    """
    When method post
    Then status 400
    And match response.error.stockQuantity == "La quantité en stock ne peut pas être négative"
