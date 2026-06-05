Feature: Create order

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Create order with valid data
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

    Given url baseUrl + '/customers'
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "order.valid@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Laptop",
      "description": "Gaming laptop",
      "price": 5000,
      "stockQuantity": 10,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": #(customerId),
      "items": [
        {
          "productId": #(productId),
          "quantity": 2
        }
      ]
    }
    """
    When method post
    Then status 201
    And match response.customer.id == customerId

  Scenario: Create order with non existing customer
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
      "stockQuantity": 10,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": 9999,
      "items": [
        {
          "productId": #(productId),
          "quantity": 1
        }
      ]
    }
    """
    When method post
    Then status 404
    And match response.error == "Customer not found"

  Scenario: Create order with non existing product
    Given url baseUrl + '/customers'
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "order.product@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": #(customerId),
      "items": [
        {
          "productId": 9999,
          "quantity": 1
        }
      ]
    }
    """
    When method post
    Then status 404
    And match response.error contains "Product not found"

  Scenario: Create order with invalid quantity
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

    Given url baseUrl + '/customers'
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "order.qty@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Mouse",
      "description": "Wireless mouse",
      "price": 100,
      "stockQuantity": 10,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": #(customerId),
      "items": [
        {
          "productId": #(productId),
          "quantity": 0
        }
      ]
    }
    """
    When method post
    Then status 400
    And match response.error contains "quantité"

  Scenario: Create order with insufficient stock
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

    Given url baseUrl + '/customers'
    And request
    """
    {
      "firstName": "Hajar",
      "lastName": "Malki",
      "email": "order.stock@gmail.com",
      "phone": "0600000000"
    }
    """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Keyboard",
      "description": "Keyboard",
      "price": 200,
      "stockQuantity": 2,
      "category": {
        "id": #(categoryId)
      }
    }
    """
    When method post
    Then status 201
    * def productId = response.id

    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": #(customerId),
      "items": [
        {
          "productId": #(productId),
          "quantity": 5
        }
      ]
    }
    """
    When method post
    Then status 400
    And match response.error contains "Stock insuffisant"