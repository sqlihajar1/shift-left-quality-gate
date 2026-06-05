Feature: Delete order

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Delete order with valid ID
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
      "email": "order.delete@gmail.com",
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
          "quantity": 1
        }
      ]
    }
    """
    When method post
    Then status 201
    * def orderId = response.id

    Given url baseUrl + '/orders/' + orderId
    When method delete
    Then status 204

  Scenario: Delete order with non existing ID
    Given url baseUrl + '/orders/' + '9999'
    When method delete
    Then status 404
    And match response.status == 404
    And match response.error == "Commande non trouvée"