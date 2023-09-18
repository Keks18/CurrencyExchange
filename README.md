# Currency Exchange API

This is a simple Currency Exchange API that allows you to manage currencies and exchange rates. It provides various endpoints to perform currency-related operations.

## Technologies / Tools Used

- JDBC
- Java Servlets
- MySQL
- Tomcat
- Postman
- Maven

## TO RUN
#### 1. MySQL Database:
  - Execute the scripts provided in the resources sequentially to create the database and necessary tables.
  - Optionally, populate the tables with test data or do this using Postman.

#### 2. Connect Tomcat (9.0.73) to the project.

#### 3. Specify your database connection parameters in resources/db.properties.

#### 4. You can conveniently test the running application using the provided Postman collection in the resources folder.

#### 5. Run the application in IntelliJ IDEA or build it locally and run it on a specific port. Then, connect to it using Postman for making requests.

## Examples of Requests to API on Postman

### Currencies

#### GET `/currencies`

Returns list of all currencies. Example of response:

```json
[
  {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  {
    "id": 1,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
  },
  "..."
]
```

#### GET `/currency/USD`

Returns particular currency. The currency code is specified in the query address Example of response:

```json
[
  {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  }
]
```

#### POST `/currencies`

Adding a new currency to the database. Data is passed in the body of request in the x-www-form-urlencoded. The form
fields are `name`, `code`, `symbol`. Example of response (inserted record):

```json
[
  {
    "id": 2,
    "name": "Czech Koruna",
    "code": "CZK",
    "sign": "Kč"
  }
]
```

### Exchange rates

#### GET `/exchangeRates`

Returns list of all exchange rates. Example of response:

```json
[
  {
    "id": 0,
    "baseCurrency": {
      "id": 0,
      "name": "United States dollar",
      "code": "USD",
      "sign": "$"
    },
    "targetCurrency": {
      "id": 1,
      "name": "Euro",
      "code": "EUR",
      "sign": "€"
    },
    "rate": 0.93
  },
  {
    "id": 1,
    "baseCurrency": {
      "id": 0,
      "name": "United States dollar",
      "code": "USD",
      "sign": "$"
    },
    "targetCurrency": {
      "id": 2,
      "name": "Czech Koruna",
      "code": "CZK",
      "sign": "Kč"
    },
    "rate": 22.16
  },
  "..."
]
```

#### POST `/exchangeRates`

Adding a new exchange rate to the database. Data is passed in the body of request in the x-www-form-urlencoded. The form
fields are `baseCurrencyCode`, `targetCurrencyCode`, `rate`. Example of response (inserted record):

```json
[
  {
    "id": 2,
    "baseCurrency": {
      "id": 1,
      "name": "Euro",
      "code": "EUR",
      "sign": "€"
    },
    "targetCurrency": {
      "id": 2,
      "name": "Czech Koruna",
      "code": "CZK",
      "sign": "Kč"
    },
    "rate": 23.75
  }
]
```

#### GET `/exchangeRate/USDEUR`

Returns a particular exchange rate. The currency pair is specified by consecutive currency codes in the query address.
Example of response:

```json
[
  {
    "id": 0,
    "baseCurrency": {
      "id": 0,
      "name": "United States dollar",
      "code": "USD",
      "sign": "$"
    },
    "targetCurrency": {
      "id": 1,
      "name": "Euro",
      "code": "EUR",
      "sign": "€"
    },
    "rate": 0.93
  }
]
```

#### PATCH `/exchangeRate/USDEUR`

Updates the existing exchange rate in the database. The currency pair is specified by consecutive currency codes in the
query address. The data is passed in the body of the request in the x-www-form-urlencoded. The only form field
is `rate`.
Example of response (inserted record):

```json
[
  {
    "id": 1,
    "baseCurrency": {
      "id": 0,
      "name": "United States dollar",
      "code": "USD",
      "sign": "$"
    },
    "targetCurrency": {
      "id": 2,
      "name": "Czech Koruna",
      "code": "CZK",
      "sign": "Kč"
    },
    "rate": 22.24
  }
]
```

## Currency exchange

#### GET `/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT`

Calculate the conversion of a particular amount of money from one currency to another. The currency pair and amount is
specified in the query address. Example of response:

```json
{
  "baseCurrency": {
    "id": 0,
    "name": "United States dollar",
    "code": "USD",
    "sign": "$"
  },
  "targetCurrency": {
    "id": 2,
    "name": "Czech Koruna",
    "code": "CZK",
    "sign": "Kč"
  },
  "rate": 22.24,
  "amount": 100.00,
  "convertedAmount": 2224.00
}
```
