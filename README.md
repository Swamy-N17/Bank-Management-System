# Bank Management System

A RESTful Bank Management System developed using **Java, Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL**.

The application provides APIs to manage banks, addresses, accounts, and account transactions such as deposits, withdrawals, and fund transfers. It also implements business validations and exception handling.

## Features

### Bank Management
- Create, update, and delete banks
- Fetch all banks
- Fetch bank by ID
- Fetch bank by IFSC code
- Fetch bank by contact number
- Fetch bank by city
- Fetch bank by address
- Pagination and sorting
- Prevent deletion of a bank if it has associated accounts

### Address Management
- Create address
- Fetch address by ID
- Update address
- Fetch address associated with a bank

### Account Management
- Create and delete accounts
- Fetch all accounts
- Fetch account by ID
- Fetch account by account number
- Fetch accounts by bank
- Fetch accounts by account type
- Fetch accounts based on balance
- Sort accounts

### Account Transactions
- Deposit amount
- Withdraw amount
- Transfer amount between accounts
- Sufficient balance validation
- Minimum balance validation
- Transaction amount validation
- Sender and receiver validation

## Business Validations

### Bank
- IFSC code must be unique
- Contact number must be unique
- Contact number must contain 10 digits
- Address is required
- PIN code must contain 6 digits
- PIN code must be unique
- Bank cannot be deleted if it has associated accounts

### Account
- Account number must be unique
- Account must belong to an existing bank
- Supported account types:
  - `SAVINGS`
  - `CURRENT`
  - `SALARY`
- Minimum balance is maintained according to account type

### Transactions
- Amount must be greater than zero
- Withdrawal requires sufficient balance
- Minimum balance must be maintained after withdrawal
- Sender and receiver accounts must exist
- Sender and receiver cannot be the same
- Sender must have sufficient balance
- Minimum balance must be maintained after transfer

## Technology Stack

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Maven**
- **REST APIs**
- **Postman**

## Project Architecture

The application follows a layered architecture.

Request
Client / Postman → Controller → Service → Repository → PostgreSQL Database

Response
Client / Postman ← Controller ← Service ← Repository ← PostgreSQL Database

## Key Features

- Bank and Address management
- Account management
- Deposit and withdrawal
- Fund transfer
- Pagination and sorting
- Account filtering
- Business validations
- Custom exception handling

## How to Run

1. Clone the repository.
2. Create a PostgreSQL database.
3. Update the database configuration in `application.properties`.
4. Run the Spring Boot application.
5. Test the REST APIs using Postman.
