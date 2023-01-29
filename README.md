# Cabify Mobile Challenge

Besides providing exceptional transportation services, Cabify also runs a physical store which sells Products.

Our list of products looks like this:

``` 
Code         | Name                |  Price
-------------------------------------------------
VOUCHER      | Cabify Voucher      |   5.00€
TSHIRT       | Cabify T-Shirt      |  20.00€
MUG          | Cabify Coffee Mug   |   7.50€
```

Various departments have insisted on the following discounts:

 * The marketing department believes in 2-for-1 promotions (buy two of the same product, get one free), and would like to have a 2-for-1 special on `VOUCHER` items.

 * The CFO insists that the best way to increase sales is with discounts on bulk purchases (buying x or more of a product, the price of that product is reduced), and demands that if you buy 3 or more `TSHIRT` items, the price per unit should be 19.00€.

Cabify's checkout process allows for items to be scanned in any order, and should return the total amount to be paid.

Examples:

    Items: VOUCHER, TSHIRT, MUG
    Total: 32.50€

    Items: VOUCHER, TSHIRT, VOUCHER
    Total: 25.00€

    Items: TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT
    Total: 81.00€

    Items: VOUCHER, TSHIRT, VOUCHER, VOUCHER, MUG, TSHIRT, TSHIRT
    Total: 74.50€


# To do
- Implement an app where a user can pick products from a list and checkout them to get the resulting price. No need to implement any real payment system, but we do need a nice user experience where our customers can understand what items are they purchasing, the price and the discount we are applying.
- You should fetch the list of products from [here](https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json).

# Considerations
- Be aware that the discounts are going to change frequently. So the code should support that.
- There is no need for a user login screen.
- Be written as production-ready code. We would like you to build it in the same way as if you were going to publish to the store.
- Consider explaning the solution and why certain things are included and others are left out.
- Be written either in Swift or Kotlin, preferably in the latest stable version of the language.
- If possible, use the latest stable version (no Betas) of Xcode or Android Studio.

# Resolution

For the resolution of this challenge, the latest technologies used in the market were implemented

- Environment Android 
  - Kotlin

- Data
  - Room
  - Flows
  - Retrofit

- Design Patterns  
  - MVVM (Viemodels, StateFlow)
  - Clean Architecture (UI - Domain - Data)

- UI
  - Compose (Screens, UI Components, UI State)

- Dependency injection
  - Dagger Hilt
  
- Coil (To get image from network)

- Unit tests (Usescases)

# Screenshots:
Product List
<br><br>
<img src="https://user-images.githubusercontent.com/25715124/216851519-874638e5-2399-4102-a835-729630c2c031.png" width="50%" height="50%">
<br><br>

Add Product
<br><br>
<img src="https://user-images.githubusercontent.com/25715124/216851584-214afc16-8ca1-4f40-b2c8-59b42babffca.png" width="50%" height="50%">
<br><br>

Current Order
<br><br>
<img src="https://user-images.githubusercontent.com/25715124/216851603-24b16878-87cf-4887-9e70-16dbf3a04b08.png" width="50%" height="50%">
<br><br>

# Considerations:

 - I used another endpoint with the same structure as the indicated endpoint to add more information (I added a image field).
 - The discount is calculated when the products are added into the order, each product can be added as many times as you want and in the Current Order Screen they are grouped by product and the discount is recalculated.
 - Pressing BUY button closes the order and user can create another one by adding new products from the list.
 - A couple of test cases were created to test the basics features, but many more could be created.
 - The types of discounts with their prices are obtained and processed in the specific UseCase (they could be obtained from an endpoint or database but is out of this scope)


Mauricio Kfouri
