# StoreManagement API App

The application implements the following functionalities:

- _API Endpoints for CRUD operations_
- _Unit tests for Rest controller_
- _Exception Handling_
- _Logging Mechanism_
- _Basic Authentication_
- _Role base endpoint access_

The Product model is simple having an auto-populated id, name and price. Two tests products are loaded into the repository at the start of the application by ProductGenerator.class.

## API Endpoints

The following APIs have been implemented inside ProductController.class:

| TYPE | Path                      | Parameters                   | Level Access |
|------|---------------------------|------------------------------|--------------|
| GET  | `/products/`              |                              | USER & ADMIN |
| GET  | `/products/{id}`          | Product ID                   | USER & ADMIN |
| POST | `/products/add`           | Product Name & Product Price | ADMIN        |
| GET  | `/products/all`           |                              | USER & ADMIN |
| POST | `/products/changePrice`   | Product ID & New Price       | ADMIN        |
| POST | `/products/deleteProduct` | Product ID                   | ADMIN        |

## Users

Basic Authentication has been implemented using Spring Security in SecurityConfig.class.

The following 2 users have been created for testing the solution:

| ROLE  | Username | Password  |
|-------|----------|-----------|
| USER  | user     | userPass  |
| ADMIN | admin    | adminPass |

## Custom Exceptions

Three custom exceptions were created based on possible scenarios:

- _EmptyProductNameException_: For empty/blank space product names
- _NegativePriceException_: For prices under 0
- _ProductNotFoundException_: If the users searches for a product that does not exist

The exceptions are handled inside ProductExceptionsController.class alongside other unchecked exceptions that might appear.

## Logging

Logging was handled using a slf4j logger inside ProductController.class.

Only two logging levels were used in the application:

- INFO: logging information about accessing the endpoints and the result
- ERROR: logging information in case the API call failed

The logs will be displayed in the console and also stored inside the project directory under _logs/application.log_

The format of the logs is defined in _resources/logback-spring.xml_
