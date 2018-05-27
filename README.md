#### Functional Requirements:
* Write a simple inventory management system. User should be able to:
* Enter new product (product details are listed below)
* Find product by name/brand
* Update/remove product
* See all leftovers on a separate page (leftovers means product which quantity is less than 5)

#### Product details:
* Name
* Brand
* Price
* Quantity

It should be two users - admin (can create, add, remove) and user with read-only access.

#### Non-functional requirements:
* All application layers should be covered with tests
* This system should be written using spring boot
* All data should be accessed using REST API.
* Application should be compliant with 12factor apps approach (https://12factor.net)

#### Bonus Tasks
Bonus tasks are optional, but shows more advanced expertise. Feel free to implement any number of those or add and describe something by
yourself.

* Export search result as an xls file
* Application should run in docker container.
* Consider more complex security mechanism like JWT.
* Provide virtual configs for 2 envs (staging and prod)
* Add instrumentation to your code where you think it's reasonable (a way to check knowledge about metrics, logging etc.)

Publish results on github. Commit history is desired. 