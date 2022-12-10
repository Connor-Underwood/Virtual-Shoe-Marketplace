https://docs.google.com/document/d/1qswNuX3YMl86KA9JH3hTofYbP_9wz7kxpe1sbmM9zDE/edit?usp=sharing
<!-----

Yay, no errors, warnings, or alerts!

Conversion time: 3.459 seconds.


Using this Markdown file:

1. Paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β33
* Fri Dec 09 2022 16:17:27 GMT-0800 (PST)
* Source doc: Project 5 ReadMe
* Tables are currently converted to HTML tables.
----->


**Project 5 ReadME**

The marketplace that our team has decided to design is for shoes. We have created five classes in the implementation, each performing a specific role of a party responsible for the effective functioning of a shoe market: the sellers, the stores, the customers, the shoes, and the marketplace itself.

The central coordinating class is the _Marketplace_ class. The user will interact with this class through the client class throughout his visit to the shoe market. A user begins his interaction with the _Marketplace/Client_ class by either logging in or creating a new account, a functionality we achieved by creating an _Accounts_ file. Once the login process is complete, we check if the user profile is a seller or a customer. Once we know that, we create an object for the user associated with the type of class: _Seller_ or _Buyer_. Thereon, _Marketplace _will be the center of interaction between _Buyers_ and _Sellers _with _Stores _and _Products_. Each _Seller_ has multiple _Stores_, and each _Store_ has multiple _Shoes_. The same has been implemented by creating an ArrayList of _Stores _for each _Seller_, and an ArrayList of _Shoes_ for each _Store_. 

Once the _Seller_ logs in, he is presented with multiple options on a switch case menu. Methods implemented in different classes, according to the menu options, are called from the _Marketplace/Serverr, _and the desired results are achieved. The menu options for the _Seller_ include:



1. To edit a _Shoe_ in one of his _Stores:_ which includes a menu of which part of the product they wished to edit - name, price, description, quantity, or shifting the product to a different store. 
2. To edit the _Store:_ which includes adding and removing _shoes_ from a _store_. 
3. To view all the _stores_ owned by the _seller_, accompanied by information on each purchase made by _customers_ in those _stores_.
4. To add more _Stores_. 

Similarly, a menu for _Customers_ is also displayed. They can:



1. View the market product-wise. 
2. Search for the product they want by the name of the _shoe_, the description of the _shoe_, the name of the _store_, and the maximum price for the _shoe_, or filter out the out-of-stock _shoes_.
3. Customers can also view their purchase history with the _shoe_ data, all of which will be stored in a file. 

The selective features we implemented are <span style="text-decoration:underline;">Files</span> and <span style="text-decoration:underline;">Statistics</span>. For <span style="text-decoration:underline;">Files</span>: all the input and output data persists in the form of CSV files for all the _sellers_ in the _Sellers_' common file. Correspondingly, the same has been implemented for _customers_ and their files. The seller can import or export products from a file, and the customer can export their purchase history to any file location. 

In addition to that, we have implemented the <span style="text-decoration:underline;">Statistics</span> which each customer or seller can view. The seller can view the statistics of the store which they have created and they can view the purchase history of each store and each customer. The customer can view the purchase history from each store and they can export their purchases.

This project uses 7 different classes, namely, Server, Client, Marketplace, Store, Shoe, Seller, and Customer. 

DISCLAIMER: PRESSING “EXIT” OR “CANCEL” ON ANY OF THE JOPTIONPANES WILL TERMINATE THE PROGRAM IMMEDIATELY

**Server Class:**

This class is the server class where all the processing and returning is done for the user. All the users connect to this class and it runs all of the switch cases for the different menu options. 

We have a server class which establishes a serversocket connection and has methods which open and close the server. We have another static clienthandler class which is called everytime we run a thread. We have a run method. This class has a customer and seller object, which give calls for the methods which are required to implement the menus. We have multiple menus each of which implements a specific case within the marketplace. We return everything to the client class and dont display anything on the server. There are checks for exceptions. An object input and object output stream, is used to write strings back and forth between the sever and the client. The menus are a reflection of the marketplace menus.

**Fields**


<table>
  <tr>
   <td>Name
   </td>
   <td>Type 
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>clientSocket
   </td>
   <td>Socket
   </td>
   <td>To accept the socket connection coming from the client class
   </td>
  </tr>
  <tr>
   <td>reader
   </td>
   <td>BufferedReader
   </td>
   <td>To read input from the clients socket 
   </td>
  </tr>
  <tr>
   <td>writer
   </td>
   <td>PrintWriter
   </td>
   <td>To write output back to the client after processing
   </td>
  </tr>
  <tr>
   <td>oos
   </td>
   <td>ObjectOutputStream
   </td>
   <td>To write longer input back to the client
   </td>
  </tr>
  <tr>
   <td>ois
   </td>
   <td>ObjectInputStream
   </td>
   <td>To read input taken in from the client
   </td>
  </tr>
  <tr>
   <td>
   </td>
   <td>
   </td>
   <td>
   </td>
  </tr>
</table>


**Client Class:**

This is the class we use for all of the input and the output. We output all of our code in JOptionPanes. It is basically a reflection of the switch case in the marketplace class, and we have Customer and Seller objects, which implement methods which do all of the processing for the server. The inputs are taken in from the keyboard via JOptionPanes, and each each seller has a menu which can make sure that they can add stores, edit shoes, add shoes to stores, remove shoes, view their sales records from different stores with different customers, importing and exporting some of their products, and changing their email and password. The customer menu, on the other hand, has the following options of: viewing the entire market, searching the market for specific products, reviewing their purchase history, exporting their purchase history, purchasing a shoe, and changing their email and password. 

**Fields used:**


<table>
  <tr>
   <td>Name
   </td>
   <td>Type
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>reader
   </td>
   <td>BufferedReader
   </td>
   <td>To read input sent back by the server
   </td>
  </tr>
  <tr>
   <td>writer
   </td>
   <td>PrintWriter
   </td>
   <td>To write the input to the server
   </td>
  </tr>
  <tr>
   <td>oos
   </td>
   <td>ObjectOutputStream
   </td>
   <td>To write longer input back to the server
   </td>
  </tr>
  <tr>
   <td>ois
   </td>
   <td>ObjectInputStream
   </td>
   <td>To read longer input taken in from the server
   </td>
  </tr>
</table>


**MarketPlace Class:**

This is the main class of our project. It was the main class which would be run for project 4 but in project 5, it is the blueprint of both the server and the client. The class contains all of the details which are needed to implement both the server and the client. The methods this class contain are:



* loadMarket: This method populates all of the arrays within the seller, customer, store, and shoe classes. It is the method which makes sure that the data persists. It is called in the start of the server class since the market needs to load before any activity can be performed within it. 
* checkEmail : this method checks the email everytime it is called. It is a verification method and is used in many occurrences. 
* checkPin : checks the seller pin. Every seller and customer is associated with a PIN which iis their identity which is only known to us but not to them. This helps us identify if it is a seller or a customer. 
* checkPinFromCredentials: This method checks the Pin when the users credentials are entered, and sent to it. 
* checkPassword : This method allows the user to login by checking the password associated with a users login username. 
* createAccountsFile : this method creates a file for the accounts if one already doesnt exist, when the market is initially created. 
* viewStoreStatistics : This method returns all of the stores that were created. It can sort the sotres according to the number of sales it made. 
* Main method: this method handles the menus, and the login. Once the user is logged in, or a new account is created, the method runs through all of the associated code with the user type and gives all of the desired outputs to navigate the market.

**Fields:**


<table>
  <tr>
   <td>Name
   </td>
   <td>Type 
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>WELCOME_MESSAGE
   </td>
   <td>String
   </td>
   <td>Welcomes the user to the program
   </td>
  </tr>
  <tr>
   <td>INVALID_VALUE
   </td>
   <td>String
   </td>
   <td>Returns this if the pin entered for login is not valid
   </td>
  </tr>
  <tr>
   <td>INVALID_EMAIL
   </td>
   <td>String
   </td>
   <td>Returns this if the pin entered for login is not valid
   </td>
  </tr>
  <tr>
   <td>TAKEN_EMAIL
   </td>
   <td>String
   </td>
   <td>Returns this if the email is already taken. 
   </td>
  </tr>
  <tr>
   <td>INVALID_PASSWORD
   </td>
   <td>String
   </td>
   <td>Makes sure the password is greater than 5 characters. 
   </td>
  </tr>
  <tr>
   <td>ACCOUNT_PROMPT
   </td>
   <td>String
   </td>
   <td>Prompts the user if they want to log in or create an account
   </td>
  </tr>
  <tr>
   <td>ENTER_YOUR_EMAIL
   </td>
   <td>String
   </td>
   <td>Prompts the user to input an email
   </td>
  </tr>
  <tr>
   <td>LOGIN_PASSWORD_PROMPT
   </td>
   <td>String
   </td>
   <td>Prompts the user to enter their password
   </td>
  </tr>
  <tr>
   <td>CREATE_PASSWORD_PROMPT
   </td>
   <td>String
   </td>
   <td>Prompots the user to create  password more than 5 characters long
   </td>
  </tr>
  <tr>
   <td>CUSTOMER_OR_SELLER
   </td>
   <td>String
   </td>
   <td>Prompts the user to select if they are a customer or a seller
   </td>
  </tr>
  <tr>
   <td>LOGIN_SUCCESSFUL
   </td>
   <td>String
   </td>
   <td>Tells the user they were able to log in sucessfully
   </td>
  </tr>
  <tr>
   <td>WRONG_EMAIL
   </td>
   <td>String
   </td>
   <td>Returns this if a wrong email was entered.
   </td>
  </tr>
  <tr>
   <td>WRONG_PASSWORD
   </td>
   <td>String
   </td>
   <td>Returns this if a wrong password was inputted
   </td>
  </tr>
  <tr>
   <td>ADD_STORE
   </td>
   <td>String
   </td>
   <td>Seller Menu option to add a store
   </td>
  </tr>
  <tr>
   <td>ADD_SHOE
   </td>
   <td>String
   </td>
   <td>Seller Menu option to add a shoe to a store
   </td>
  </tr>
  <tr>
   <td>EDIT_SHOE
   </td>
   <td>String
   </td>
   <td>Seller Menu option to edit a shoe
   </td>
  </tr>
  <tr>
   <td>REMOVE_SHOE
   </td>
   <td>String
   </td>
   <td>Seller Menu option to remove a shoe
   </td>
  </tr>
  <tr>
   <td>VIEW_STATISTICS
   </td>
   <td>String
   </td>
   <td>Seller Menu option to view the statistics of the purchases
   </td>
  </tr>
  <tr>
   <td>CHANGE_SELLER_EMAIL
   </td>
   <td>String
   </td>
   <td>Seller Menu option to change the email
   </td>
  </tr>
  <tr>
   <td>CHANGE_SELLER_PASSWORD
   </td>
   <td>String
   </td>
   <td>Seller Menu option to change the password
   </td>
  </tr>
  <tr>
   <td>IMPORT_PRODUCTS
   </td>
   <td>String
   </td>
   <td>Seller Menu option to import products from a file
   </td>
  </tr>
  <tr>
   <td>EXPORT_PRODUCTS
   </td>
   <td>String
   </td>
   <td>Seller Menu option to export products using a file to do so.
   </td>
  </tr>
  <tr>
   <td>VIEW_MARKET
   </td>
   <td>String
   </td>
   <td>Seller Menu option to view the entire marketplace and its products for the customer
   </td>
  </tr>
  <tr>
   <td>SEARCH_MARKET
   </td>
   <td>String
   </td>
   <td>Customer Menu option to search the market for the products it offers
   </td>
  </tr>
  <tr>
   <td>REVIEW_PURCHASE_HISTORY
   </td>
   <td>String
   </td>
   <td>Customer menu option to review the purchase history as a table
   </td>
  </tr>
  <tr>
   <td>EXPORT_SHOE
   </td>
   <td>String
   </td>
   <td>Customer menu option to export their purchases
   </td>
  </tr>
  <tr>
   <td>CHANGE_CUSTOMER_EMAIL
   </td>
   <td>String
   </td>
   <td>Customer menu option to change their email
   </td>
  </tr>
  <tr>
   <td>CHANGE_CUSTOMER_PASSWORD
   </td>
   <td>String
   </td>
   <td>Customer menu option to change their password
   </td>
  </tr>
  <tr>
   <td>PURCHASE_SHOE
   </td>
   <td>String
   </td>
   <td>Customer menu option to purchase a shoe
   </td>
  </tr>
  <tr>
   <td>VIEW_MARKET_STATISTICS
   </td>
   <td>String
   </td>
   <td>Customer menu option to view the statistics of the market
   </td>
  </tr>
  <tr>
   <td>sellers
   </td>
   <td>ArrayList&lt;Seller>
   </td>
   <td>Arraylist of all of the sellers within the marketplace
   </td>
  </tr>
  <tr>
   <td>customers
   </td>
   <td>ArrayList&lt;Customer>
   </td>
   <td>Arraylist of all of the customers in the marketplace
   </td>
  </tr>
  <tr>
   <td>random
   </td>
   <td>Random
   </td>
   <td>Random number generation variable
   </td>
  </tr>
</table>


**Seller class:**

This class contains all of the methods and variables that are required to create a working seller implementation. It has multiple methods that are called within the marketplace, and basically makes sure that the server menu runs properly.  The methods which we created are:



* getEmail: this is a getter method which returns the email username of the seller
* setEmail : This is a setter method but it also writes to the file and updates the email everywhere
* getPassword:getter method to return the password
* setPassword: This method sets the password and makes sure that it is also updated within the files. 
* getPin: getter which returns the pin associated with the seller
* equalsSeller: Makes sure that the seller entered is equal to the current seller
* checkStore: checks if that seller owns that store and checks if the store is valid
* addStore: adds a store associated to that seller. It writes to the arraylist of stores and to the file so that the data will persist.
* addShoe: adds a shoe in the desired store associated to that seller. It writes to the arraylist of stores and shoes and to the file so that the data will persist.
* removeShoe: removes the shoe from the arraylist of the shoes. It also removes the shoe from the file. 
* getStores: this is a getter method which returns the stores arraylist from that seller
* viewStatistics: This method takes input parameters which sorts the seller purchase data by the customer or the store, allowing the user to have more customization of how they view their stores sales
* importProducts: This method takes input from files and adds the products to the store specified in the file. It adds them to the arraylist and to the market file, just as though the seller is importing the products from some other place. 
* exportProducts: this method takes some products from the sellers store, and exports them as a file to another place. 

    **Fields:**


<table>
  <tr>
   <td>
<strong>Name </strong>
   </td>
   <td><strong>Type </strong>
   </td>
   <td><strong>Description</strong>
   </td>
  </tr>
  <tr>
   <td>stores
   </td>
   <td>ArrayList&lt;stores>
   </td>
   <td>Contains all of the stores that are associated with that seller
   </td>
  </tr>
  <tr>
   <td>Pin 
   </td>
   <td>String
   </td>
   <td>Contains the unique pin that is associated with that seller
   </td>
  </tr>
  <tr>
   <td>email
   </td>
   <td>String
   </td>
   <td>Contains the sellers username orr email
   </td>
  </tr>
  <tr>
   <td>password
   </td>
   <td>String
   </td>
   <td>Contains the sellers account password
   </td>
  </tr>
</table>


**Customer Class:**

This is the customer equivalent of the seller class. We have multiple methods which are used to implement each of the customer menus. Each one has a specific purpose, and in addition to the getters and setters, are used throughout the server, marketplace, and customer classes. The methods we implement are:



* addPurchaseHistory: This method adds lines to the purchase history arraylist, which is written to the file.
* addTotalAmount: This method adds the amount of the purchase to the amount that the customer has bought
* getTotalAmount: This is a getter returning the total amount that the customer spent
* getPurchaseHistory: this is a getter for the arraylist of all of the purchases that the customer processed
* getEmail: this is a getter method which returns the email username of the customer 
* setEmail : This is a setter method but it also writes to the file and updates the email everywhere
* getPassword:getter method to return the password of the customer.
* setPassword: This method sets the password and makes sure that it is also updated within the files. 
* getPin: getter which returns the pin associated with the customer
* equalscustomer: Makes sure that the customer entered is equal to the current customer.
* viewPurchaseHistory: this method reads from the files to create the purchase history ArrayList which is then returned as a string to the client class
* viewMarket: this reads from the market file and displays all of the names, descriptions, and the prices. All of this is done in double dimensional arrays to get a table format. There are multiple different ways the method works, we can sort it according to the users choice, by the various methods with the search parameter, which are price, description, name, and quantity and gives only the desired results.
* purchase: this method processes the purchase that is being conducted by the customer. It edits the file and makes sure that the file is correctly updated for the purchase. The purchaseHistory arraylist is also updated, and the lines are also added
* findSeller: This method finds the required seller in the marketplace and compares it to the entered seller.
* findShoe: This method finds the shoe within the required store and the seller. We find the shoe that is needed and it is returned to the client.

<table>
  <tr>
   <td>
Name
   </td>
   <td>Type
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>pin
   </td>
   <td>String
   </td>
   <td>To store the pin associated with the customer
   </td>
  </tr>
  <tr>
   <td>email
   </td>
   <td>String
   </td>
   <td>To store the email ID associated with the customer
   </td>
  </tr>
  <tr>
   <td>password
   </td>
   <td>String
   </td>
   <td>To store the password that the customer has saved the account with
   </td>
  </tr>
  <tr>
   <td>purchaseHistory
   </td>
   <td>ArrayList&lt;Shoe>
   </td>
   <td>Arraylist of shoes that have been purchased by the customer
   </td>
  </tr>
  <tr>
   <td>totalAmountPurchased
   </td>
   <td>Integer
   </td>
   <td>Totol amount of purchases made by the customer
   </td>
  </tr>
</table>


**Store Class:**

This class is the intermediate class between the seller and the customer. The seller interacts with the marketplace and the customers through their stores. The class implements some of the seller functionality, everything that connects the seller to the marketplace.

Methods used:



* getRevenue: getter to return the revenue generated from that specific store. 
* setRevenue: setter method for the revenue generated by that store
* getSeller: getter method returning the name of the seller who owns that store
* addShoe: This method adds a specific shoe to that store, implemented in the seller menu and writes to the files.
* removeShoe: This method removes a specific shoe from that store, implemented in the seller menu and writes to the files.
* editShoe: This method uses the addShoe and the removeShoe method to change the details of the shoe, and creates a new one in place of the old one. It then updates the files with the edited shoes.
* getName: getter to retur the name of that specific store
* getShoes: This method is a getter for the shoes that exist within that store. They are stores in an arraylist.
* getSales: This getter method returns the sales made from this store
* setSales: Setter method to set the sales made by that store. (sales is an integer which is the number of sales made by that store)
* getCustomers:This returns the arraylist of customers who have shopped at that store.
* addCustomer: This method adds a customer to the arraylist of customers who have shopped at that store
* equalsStore: This method checks if the store is equal to another store that is being searched by that client. 
* checkShoe: This method checks if the shoe entered by the user is equal to a shoe in the arraylist of that store.

Fields:


<table>
  <tr>
   <td>Name
   </td>
   <td>Type
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>seller
   </td>
   <td>Seller
   </td>
   <td>This is the seller which owns the store 
   </td>
  </tr>
  <tr>
   <td>shoes
   </td>
   <td>Arraylist&lt;Shoe>
   </td>
   <td>These are all of the shoes which are in this store
   </td>
  </tr>
  <tr>
   <td>name
   </td>
   <td>String
   </td>
   <td>Name of the store
   </td>
  </tr>
  <tr>
   <td>revenue
   </td>
   <td>double
   </td>
   <td>Revenue of the store generated till now
   </td>
  </tr>
  <tr>
   <td>customers
   </td>
   <td>Arraylist&lt;customers>
   </td>
   <td>All of the customers that have purchased something from the store
   </td>
  </tr>
</table>


**Shoe Class:**

This is the most basic class that is implemented. Most of the objects that we deal with throughout this project are of the type shoe. This class gives them their functionality and their attributes.

The methods in this class are:



* getStore: This method returns the name of the store which the shoe belongs to.
* setStore: setter which changes the store to which this shoe belongs.
* getName: getter which gets the name of this pair of shoes.
* setName: setter which can change the name of the shoe.
* getDescription: getter which returns the description of the pair of shoes.
* setDescription: setter which updates the description of the pair of shoes.
* getQuantity: getter which returns the quantity of the pair of shoes.
* setQuantity: setter which changes and sets the quatity of the pair of shoes.
* getPrice: getter which returns the price of the pair of shoes.
* setPrice: setter which edits the price of the pair of shoes.
* equalsShoe: this method checks if an entered shoe is equal to that current shoe

Fields:


<table>
  <tr>
   <td>Name 
   </td>
   <td>Type
   </td>
   <td>Description
   </td>
  </tr>
  <tr>
   <td>store
   </td>
   <td>Store
   </td>
   <td>Contains the store the shoe belongs to.
   </td>
  </tr>
  <tr>
   <td>name
   </td>
   <td>String
   </td>
   <td>Name of the shoe
   </td>
  </tr>
  <tr>
   <td>description
   </td>
   <td>String
   </td>
   <td>Contains the description of the shoe
   </td>
  </tr>
  <tr>
   <td>quantity
   </td>
   <td>integer
   </td>
   <td>Contains the quantity of the shoe manufactured
   </td>
  </tr>
  <tr>
   <td>price
   </td>
   <td>double
   </td>
   <td>Contains the price of the shoe
   </td>
  </tr>
</table>


**<span style="text-decoration:underline;">Files</span>**

Format of some specified files: In many cases the files are of the format .csv each one has a different format and each value separated by a comma means something different.

**Accounts csv file**: this file stores only the details of each account of each user

Format below:

PIN,email,password

E.g. -> 1001,seller@seller.com,seller

E.g. -> 15555,customer@customer.com,customer

Seller's PIN is ALWAYS 4 DIGITS

Customer's PIN is ALWAYS 5 DIGITS

**Stores csv file**: this file contains the details for each store. We need it to make sure that we can keep track of the histories of purchases.

Format below:

sellerPIN,sellerEmail,sellerPassword,Store1,

customer1,customer1info,

customer2,customer2info

Why do we need this file?

This file is meant to store customer info from each store. Should help with Statistics selection.

We have to read from a file to load the customers arrayList in Marketplace main.

We will read from this file in loadMarket().

customerPin,customerEmail,customerPassword,sellerPin,sellerEmail,sellerPassword,Store,

product1Name,product1desc,product1price,product1quant,

15112,customer@gmail,password,1001,seller@gmail,password,Nike,Jordan Retro's, Athletic Wear,150.00,53,

15112,customer@gmail,password,1423,seller,passWORD,Nike,Cool Shoes,Gym Attire,134,12

**Market csv file: **This file is to view the entire marketplace. We need it to find a fast way to check if things exist.

Format below:

1001,email,password,store_name,shoe_name,shoe_description,shoe_price,shoe_quantity

1001,email,password,store_name2,

sellerPIN,email,password,store_name,

PIN is seller pin, which will always be 4 digits

Seller s = new Seller(arr[0], arr[1], arr[2])

Store store = new Store(arr[3], s)

**Input.csv File: **this is the file format required to input products for the seller when they decide to import shoes. The **Export csv **file is of the same format when the seller decides to export products.

Format below:

Store name,shoe name,description,price,quantity,

Nike,Nike Air Max SYSTM,very nice,100,40,

Adidas,Ultraboost 22 shoes,just cool,133,20,

Reebok,new shoe,very fast,140,30,

**TestCases**

**Login Test cases:**

**Sample1:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button

Expected result: Application verifies the user's username and password and loads their homepage automatically. 

Test Status: Passed

**Sample 2:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password less than 5 characters
5. User presses the submit button

Expected result: Application gives an error pane and tells the user to reenter a password greater than 5 characters.

Test Status: Passed

**Sample 3:**



1.  User launches application.
2.  User selects the username textbox.
3.  User enters username via the keyboard.
4.  User selects the password textbox.
5. User selects the "Log in" button. 

Expected result: Application verifies the user's username and password and loads their homepage automatically. 

Test Status: Passed. 

**Seller Test cases:**

**Sample 4:**



1.  User(Seller) launches application.
2.  User selects the username textbox.
3.  User enters username via the keyboard.
4.  User selects the password textbox.
5. User selects the "Log in" button.
6. User is redirected to their homepage 
7. User selects menu option of adding a store
8. User enters the store name

Expected result: Application verifies that a store was added successfully

Test Status: Passed. 

**Sample5:**



1. User (Seller) launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the add a shoe menu option
7. User enters each of the details of the shoe on prompt (One prompt per detail)
8. User presses submit

Expected result: Application verifies that the shoe has been added to the designated store which was entered

Test Status: Passed

**Sample6:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User presses the remove shoe option
7. User selects the add a shoe menu option
8. User enters each of the details of the shoe on prompt (One prompt per detail)
9. User presses submit

Expected result: Application verifies that the shoe has been removed

Test Status: Passed

**Sample7:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the edit product option
7. User enters the name of the product
8. User enters the store name of the product
9. User enters the new details of the product
10. User presses the submit button

Expected result: Application verifies that the shoe has been successfully edited

Test Status: Passed

**Sample8:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the view statistics method
7. User chooses to view it with no filters

Expected result: Application displays the different stores and the different customers form each of the stores

Test Status: Passed

**Sample9:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the change email option
7. User is prompted to enter a new email
8. User presses submit and the email gets changed
9. User tries to log in again with the new email

Expected result: Application verifies the user's username and password and loads their homepage automatically, as long as the new email is entered.

Test Status: Passed

**Sample10:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the change password option
7. User is prompted to enter a new password
8. User presses submit and the password gets changed
9. User tries to log in again with the new password 

Expected result: Application verifies the user's username and password and loads their homepage automatically, as long as the new passwordl is entered.

Test Status: Passed

**Sample11:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the import products option
7. User is prompted to enter a file name
8. User needs to make sure the file is of the specified format of input.csv

Expected result: Application inputs the products into their stores and updates their values

Test Status: Passed

**Sample12:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the export products option
7. Users enters the location they want to export their file to.

Expected result: Application creates a file which contains all of the store data of the user and their products.

Test Status: Passed

**Customer test cases:**

**Sample13:**



1. User (Customer) launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the view the entire market option

Expected result: Application verifies the user's username and password and loads their homepage automatically, as long as the new email is entered, it displays all of the products and all of the stores that are present in the market

Test Status: Passed

**Sample14:**



1. User (Customer) launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the option to search the market
7. User then enters the details they would like to search the market by

Expected result: Application gives the user a list of all of the results which match their search.

Test Status: Passed

**Sample15:**



1. User (Customer) launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the option review their purchase history

Expected result: Application gives the user a table containing all of their purchases including the name of the purchase, description, price, quantity, and the seller name and store.

Test Status: Passed

**Sample16:**



1. User (Customer) launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the option to export their purchase history
7. User enters the location to which they would like to export their purchase history.

Expected result: Application returns a file to the location that the user specified which is of the Export.csv format.

Test Status: Passed

**Sample17:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the change email option
7. User is prompted to enter a new email
8. User presses submit and the email gets changed
9. User tries to log in again with the new email

Expected result: Application verifies the user's username and password and loads their homepage automatically, as long as the new email is entered.

Test Status: Passed

**Sample18:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the change password option
7. User is prompted to enter a new password
8. User presses submit and the password gets changed
9. User tries to log in again with the new password 

Expected result: Application verifies the user's username and password and loads their homepage automatically, as long as the new passwordl is entered.

Test Status: Passed

**Sample19:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the purchase shoe method
7. User is given a dropdown of all of the shoes available
8. User finds the shoe and selects it
9. User enters a quantity they want to purchase which is smaller than the quantity in stock.

Expected result: Application processes the purchase and updates it in all of the logs. User gets  a purchased successfully prompt

Test Status: Passed

**Sample20:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the purchase shoe option.
7. User is given a dropdown of all of the shoes available
8. User finds the shoe and selects it
9. User enters a quantity they want to purchase which is larger than the quantity in stock.

Expected result: Application gives the user an error pane which says that there is not enough in stock. It then reprompts the user to enter a value.

Test Status: Passed

**Sample21:**



1. User launches the application
2. User is directed to the welcome screen
3. User decides to create a new account
4. User enters their email and a password greater than 5 characters
5. User presses the submit button
6. User selects the view market statistics option.

Expected result: Application shows the user a table of the number of the sellers, stores, sales, products, and their prices.

Test Status: Passed
