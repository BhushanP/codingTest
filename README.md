# codingTest

The project is a SpringBoot application utilizing the Web, JPA and H2 in-mem DB features.
The project allows end users to add/delete items to/from their cart, view their cart, view and apply promocodes available to avail.
The project allows vendors to add/delete/view promocodes.
It uses an in memory H2 DB to store the ITEMS, CART and PROMOTIONS.

1. To add promotion/promocodes use the below api:
    POST /xyz/ecommerce/promotion HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

    {
      "promoCode":"OneBOneC",
      "price":80,
      "description":"Buy B and One C for 50",
      "codeCriteria": "C:1:D:1",                  
      "discountType":"fixed",
      "discountPrice":50,
      "discountPercentage":0
    }

    *Note: the codeCriteria is mandatory and has to be given in the below String format:  
                          "itemOnDiscount1:MinnimumQuantityToAvailDiscount:itemOnDiscount2:MinnimumQuantityToAvailDiscount.......so on"
                      
                      
2. To view all available promocodes use below api:
    GET /xyz/ecommerce/promotion HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

3. To delete a promocode use the below api:
    DELETE /xyz/ecommerce/promotion/{promoCode} HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

4. To delete all promocodes use the below api:
    DELETE /xyz/ecommerce/promotion HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

5. To add items to the cart use below api:
    POST /xyz/ecommerce/cart/1 HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

    {
      "item": {"id":"A","price":50},
      "quantity":3
    }

6. To delete items from the cart use below api
    DELETE /xyz/ecommerce/cart/{cartId}/{itemId} HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
    
7. To View your cart use the below api:
    GET /xyz/ecommerce/cart/{cartId} HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json

8. To apply a promocode to you cart use the below api:
    POST /xyz/ecommerce/cart/{cartId}/{promoCode}  HTTP/1.1
    Host: localhost:8080
    Content-Type: application/json
