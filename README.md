# Digital Wallet Application

## Overview

This project is a digital wallet application with microservices architecture. It supports adding values, withdrawing values, purchases, cancellation, and refund events. The application is implemented using Spring Boot and is containerized with Docker.

## Architecture

The application follows a microservices architecture with the following components:

- **Wallet Service**: Handles wallet-related operations such as creating a wallet, adding amounts, and retrieving transactions.
- **Transaction Service**: Manages transaction records and operations.

## Setup Instructions

1. Clone the repository:
    ```bash
    git clone <repository_url>
    cd digital-wallet
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Run the application with Docker:
    ```bash
    docker-compose up --build
    ```

4. Access the application:
   - API Documentation: `http://localhost:8080/swagger-ui/`
   - H2 Console: `http://localhost:8080/h2-console`

## API Endpoints

- `POST /api/wallet`: Create a new wallet
- `POST /api/wallet/{walletId}/add`: Add amount to a wallet
- `GET /api/wallet/{walletId}/transactions`: Get wallet transactions

## Database Migrations

Database migrations are managed using Flyway. Migration scripts are located in `src/main/resources/db/migration`.

## Testing

Unit tests are located in the `src/test/java/com/digitalwallet` directory. To run the tests, use the following command:

```bash
mvn test