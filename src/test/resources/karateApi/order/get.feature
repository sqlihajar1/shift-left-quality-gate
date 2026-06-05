Feature: Get orders

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Get all orders
    Given url baseUrl + '/orders'
    When method get
    Then status 200
    And match response == '#[]'

  Scenario: Get order by valid ID
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
      "email": "order.get@gmail.com",
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
    When method get
    Then status 200
    And match response.id == orderId
    And match response.customer.id == customerId

  Scenario: Get order with non existing ID
    Given url baseUrl + '/orders/' + '9999'
    When method get
    Then status 404
    And match response.status == 404
    And match response.error == "Commande non trouvée"