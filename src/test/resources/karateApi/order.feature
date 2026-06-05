Feature: Order creation

  Background:
    * def baseUrl = 'http://localhost:8080'

  Scenario: Scenario: Create order using highest priced product

    Given url baseUrl + '/categories'
    And request
      """
      {
        "name": "électronique",
        "description": "Appareils et gadgets électroniques"
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
        "email": "hajar.malki@gmail.com",
        "phone": "00000000"
      }
      """
    When method post
    Then status 201
    * def customerId = response.id

    Given url baseUrl + '/products'
    And request
      """
      {
    "name": "Ordinateur portable",
    "description": "Ordinateur portable performant",
    "price": 4500.00,
    "stockQuantity": 10,
    "category": {
    "id": #(categoryId)
    }
    }
    """
    When method post
    Then status 201
    * def productPcId = response.id


    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Tablette",
      "description": "Tablette tactile",
      "price": 2500.00,
      "stockQuantity": 15,
      "category": {
        "id": #(categoryId)
      }
      }
      """
    When method post
    Then status 201
    * def productTabletId = response.id


    Given url baseUrl + '/products'
    And request
    """
    {
      "name": "Souris",
      "description": "Souris sans fil",
      "price": 150.00,
      "stockQuantity": 50,
      "category": {
        "id": #(categoryId)
      }
      }
      """
    When method post
    Then status 201
    * def productMouseId = response.id


    Given url baseUrl + '/orders'
    And request
    """
    {
      "customerId": #(customerId),
      "items": [
        {
          "productId": #(productPcId),
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
    And match response.customer.id == customerId

    And match each response.orderItems[*].quantity == 1
    And match each response.orderItems[*].product.id == productPcId
    #match each s’applique uniquement aux tableaux



