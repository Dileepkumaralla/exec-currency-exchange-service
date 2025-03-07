# Currency Exchange Service

## Overview
This is a currency exchange service application that calculates the payable amount after applying discounts and converting currencies using real-time exchange rates.

## Prerequisites
- **Java 21**
- **Maven**
- **MongoDB**
- **Exchange Rate API Key** (for fetching real-time currency exchange rates)

## Features
- **Bill Calculation**: Accepts bill details and calculates the net payable amount after applying discounts.
- **Currency Conversion**: Converts the final payable amount to the target currency using real-time exchange rates.
- **API Integration**: Fetches exchange rates from [ExchangeRate-API](https://www.exchangerate-api.com/) or [Open Exchange Rates](https://openexchangerates.org/).
- **Modern Coding Practices**: Follows OOP principles, clean code, and maintains test coverage.

## API Endpoint
### **Calculate Payable Amount**
**Endpoint:** `/api/calculate`
**Method:** `POST`

please go through the screen shot dir of the postman collection for the request and response details

## Design & Testing
- **Object-Oriented Design**: Uses well-structured classes and follows design principles.
- **UML Diagram**: Includes a high-level UML class diagram.
- **Unit Testing**:
    - Uses JUnit & Mockito for unit tests.
    - Mocking frameworks used for external API calls.
    - Code coverage ensured.

## Running the Application
### **1. Clone the repository**
```sh
git clone https://github.com/your-repo/currency-exchange-service.git
cd currency-exchange-service
```


### **2. Build and Run**
```sh
mvn clean install
mvn spring-boot:run
```

### **3. Running Tests**
```sh
mvn test
```

### **4. Generating Coverage Report**
```sh
mvn jacoco:report
```

## API Integration Details
- **Exchange Rate API Used**: Open Exchange Rates
- **Endpoint**: `https://open.er-api.com/v6/latest/{base_currency}?apikey=your-api-key`

## License
This project is licensed under the MIT License.

